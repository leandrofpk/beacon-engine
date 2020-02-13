package br.gov.inmetro.beacon.engine.domain.pulse;

import br.gov.inmetro.beacon.engine.application.PulseDto;
import br.gov.inmetro.beacon.engine.domain.chain.ChainDomainService;
import br.gov.inmetro.beacon.engine.domain.chain.ChainValueObject;
import br.gov.inmetro.beacon.engine.domain.repository.CombinationErrors;
import br.gov.inmetro.beacon.engine.domain.repository.EntropyRepository;
import br.gov.inmetro.beacon.engine.domain.repository.PulsesRepository;
import br.gov.inmetro.beacon.engine.domain.service.PastOutputValuesService;
import br.gov.inmetro.beacon.engine.infra.ProcessingErrorTypeEnum;
import br.gov.inmetro.beacon.engine.infra.PulseEntity;
import br.gov.inmetro.beacon.engine.infra.alerts.ISendAlert;
import br.gov.inmetro.beacon.engine.queue.BeaconVdfQueueSender;
import br.gov.inmetro.beacon.engine.queue.EntropyDto;
import br.gov.inmetro.beacon.engine.queue.PrecommitmentQueueDto;
import br.gov.inmetro.beacon.library.ciphersuite.suite0.CriptoUtilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.gov.inmetro.beacon.engine.infra.util.DateUtil.getTimeStampFormated;

@Service
public class NewPulseDomainService {

    private final Environment env;

    private final PulsesRepository pulsesRepository;

    private final EntropyRepository entropyRepository;

    private final CombinationErrors combinationErrorsRepository;

    private List<EntropyDto> regularNoises = new ArrayList<>();

    private CombineDomainResult combineDomainResult;

    private PulseEntity lastPulseEntity;

    private ChainValueObject activeChain;

    private final PastOutputValuesService pastOutputValuesService;

    private final String certificateId = "04c5dc3b40d25294c55f9bc2496fd4fe9340c1308cd073900014e6c214933c7f7737227fc5e4527298b9e95a67ad92e0310b37a77557a10518ced0ce1743e132";

    private final BeaconVdfQueueSender beaconVdfQueueSender;

    private final ISendAlert iSendAlert;

    private static final Logger logger = LoggerFactory.getLogger(NewPulseDomainService.class);

    @Autowired
    public NewPulseDomainService(Environment env, PulsesRepository pulsesRepository, EntropyRepository entropyRepository,
                                 CombinationErrors combinationErrors, PastOutputValuesService pastOutputValuesService,
                                 BeaconVdfQueueSender beaconVdfQueueSender, ISendAlert iSendAlert) {
        this.env = env;
        this.pulsesRepository = pulsesRepository;
        this.entropyRepository = entropyRepository;
        this.combinationErrorsRepository = combinationErrors;
        this.pastOutputValuesService = pastOutputValuesService;
        this.beaconVdfQueueSender = beaconVdfQueueSender;
        this.iSendAlert = iSendAlert;
    }

    @Transactional
    public void begin(List<EntropyDto> regularNoises) {
        try {

            this.regularNoises = regularNoises;

            this.activeChain = ChainDomainService.getActiveChain();
            this.lastPulseEntity = pulsesRepository.last(activeChain.getChainIndex());
            String property = env.getProperty("beacon.number-of-entropy-sources");

            combinar(activeChain.getChainIndex(), property);
            processarAndPersistir();
            cleanDiscardedNumbers();

        } catch (Exception e){
            e.printStackTrace();
            iSendAlert.sendException(e);
        }
    }

    private void combinar(long activeChain, String numberOfSources) {

        PulseDto pulseDto = null;
        if (lastPulseEntity != null){
            pulseDto = new PulseDto(this.lastPulseEntity);
        }

        CombinationEnum combinationEnum = CombinationEnum.valueOf(env.getProperty("beacon.combination"));

        CombineDomainService combineDomainService = new CombineDomainService(regularNoises, activeChain,
                new Integer(numberOfSources), pulseDto, combinationEnum);
        this.combineDomainResult = combineDomainService.processar();

        //
        if (!combineDomainResult.getCombineErrorList().isEmpty()){
            iSendAlert.sendWarning(combineDomainResult);
        }
        //
    }

    private void processarAndPersistir() throws Exception {
        if (combineDomainResult.getLocalRandomValueDtos().size() < 2){
            return;
        }

        List<LocalRandomValueDto> localRandomValueDtos = combineDomainResult.getLocalRandomValueDtos();

        // tratando primeiro registro do banco
        boolean firstPulseInBd = false;
        Pulse previousPulse = null;
        if (this.lastPulseEntity == null) {  // first pulse in BD
            firstPulseInBd = true;
        } else {
            previousPulse = Pulse.BuilderFromEntity(lastPulseEntity);
        }
        // tratando primeiro registro do banco
        // tratar primeiro registro da cadeia?

//        List<Pulse> processedPulses = new ArrayList<>();
        for (int currentIndex = 0; currentIndex < localRandomValueDtos.size(); currentIndex++) {
            // tratar previous pulse

            LocalRandomValueDto currentLocalRandom = localRandomValueDtos.get(currentIndex);
            LocalRandomValueDto nextLocalRandomValue;
            int nextIndex = currentIndex + 1;

            if (nextIndex < localRandomValueDtos.size()){
                nextLocalRandomValue = localRandomValueDtos.get(nextIndex);
            } else {
                return;
            }

            Pulse pulso;
            if (firstPulseInBd){
                pulso = getFirstPulse(currentLocalRandom);
                firstPulseInBd = false;
                previousPulse = pulso;
            } else {
                pulso = getRegularPulse( previousPulse, currentLocalRandom, nextLocalRandomValue);
                previousPulse = pulso;
            }
            persistOnePulse(pulso);
        }

    }

    private Pulse getRegularPulse(Pulse previous, LocalRandomValueDto current, LocalRandomValueDto next) throws Exception {
        List<ListValue> list = new ArrayList<>();
        list.add(ListValue.getOneValue(previous.getOutputValue(),
                "previous", previous.getUri()));

        long vPulseIndex = previous.getPulseIndex()+1;
        String uri = env.getProperty("beacon.url") +  "/beacon/" + activeChain.getVersionUri() + "/chain/" + activeChain.getChainIndex() + "/pulse/" + vPulseIndex;

//        long vPulseIndexNext = vPulseIndex+1;

        // gap de data
        int vStatusCode = 0;
        long between = ChronoUnit.MINUTES.between(previous.getTimeStamp(), current.getTimeStamp());
        if (between > 1){
            vStatusCode = 2;
            List<ListValue> listValue = previous.getListValue();

            list.add(ListValue.getOneValue(listValue.get(1).getValue(), "hour", listValue.get(1).getUri()));
            list.add(ListValue.getOneValue(listValue.get(2).getValue(), "day", listValue.get(2).getUri()));
            list.add(ListValue.getOneValue(listValue.get(3).getValue(), "month", listValue.get(3).getUri()));
            list.add(ListValue.getOneValue(listValue.get(4).getValue(), "year", listValue.get(4).getUri()));
        } else {

            if (previous.getPulseIndex() == 1){  // this is the second pulse
                List<ListValue> listValue = previous.getListValue();

                list.add(ListValue.getOneValue(listValue.get(1).getValue(), "hour", previous.getUri()));
                list.add(ListValue.getOneValue(listValue.get(2).getValue(), "day", previous.getUri()));
                list.add(ListValue.getOneValue(listValue.get(3).getValue(), "month", previous.getUri()));
                list.add(ListValue.getOneValue(listValue.get(4).getValue(), "year", previous.getUri()));
            } else {
                list.addAll(pastOutputValuesService.getOldPulses(current, uri));
            }

        }

        return new Pulse.Builder()
                .setUri(uri)
                .setChainValueObject(activeChain)
                .setCertificateId(this.certificateId)
                .setPulseIndex(vPulseIndex)
                .setTimeStamp(current.getTimeStamp())
//                .setLocalRandomValue(current.getValue())
                .setLocalRandomValue(previous.getPrecommitmentValue())
                .setListValue(list)
                .setExternal(External.newExternal())
                .setPrecommitmentValue(next.getValue())
                .setStatusCode(vStatusCode)
                .setPrivateKey(CriptoUtilService.loadPrivateKeyPkcs1(env.getProperty("beacon.x509.privatekey")))
                .build();

    }

    private Pulse getFirstPulse(LocalRandomValueDto localRandomValue) throws Exception {
        List<ListValue> list = new ArrayList<>();
        list.add(ListValue.getOneValue("00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
                "previous", null));
        list.add(ListValue.getOneValue("00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
                "hour", null));
        list.add(ListValue.getOneValue("00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
                "day", null));
        list.add(ListValue.getOneValue("00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
                "month", null));
        list.add(ListValue.getOneValue("00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
                "year", null));

        String uri = env.getProperty("beacon.url") +  "/beacon/" + activeChain.getVersionUri() + "/chain/" + activeChain.getChainIndex() + "/pulse/" + 1;


        return new Pulse.Builder()
                .setUri(uri)
                .setChainValueObject(activeChain)
                .setCertificateId(this.certificateId)
                .setPulseIndex(1)
                .setTimeStamp(localRandomValue.getTimeStamp())
                .setLocalRandomValue(localRandomValue.getValue())
                .setListValue(list)
                .setExternal(External.newExternal())
                .setPrecommitmentValue("00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000")
                .setStatusCode(1)
                .setPrivateKey(CriptoUtilService.loadPrivateKeyPkcs1(env.getProperty("beacon.x509.privatekey")))
                .build();
    }

    @Transactional
    protected void persistOnePulse(Pulse pulse) throws InterruptedException {
        sendPulseForCombinationQueue(pulse);

        Optional<PulseEntity> byChainAndTimestamp = pulsesRepository.findByChainAndTimestamp(1, pulse.getTimeStamp());

        if (!byChainAndTimestamp.isPresent()){
            pulsesRepository.save(new PulseEntity(pulse));
            combinationErrorsRepository.persist(combineDomainResult.getCombineErrorList());
            entropyRepository.deleteByTimeStamp(pulse.getTimeStamp());

            logger.warn("Pulse released:" + pulse.getTimeStamp());
        } else {
            entropyRepository.deleteByTimeStamp(pulse.getTimeStamp());
            iSendAlert.sendTimestampAlreadyPublishedException(pulse);
        }

    }

    private void sendPulseForCombinationQueue(Pulse pulse) throws InterruptedException{
        boolean isSend = Boolean.parseBoolean(env.getProperty("beacon.vdf.combination.send.precom-to-queue"));
        long pulseDelay = Long.parseLong(env.getProperty("beacon.pulse.release.delay"));

        if (isSend){
            String minutes = env.getProperty("beacon.vdf.combination.send.minutes");
            String[] split = minutes.split(",");

            Integer minute = ZonedDateTime.now().getMinute();

            for (String s : split) {
                if (s.equalsIgnoreCase(minute.toString())){
                    beaconVdfQueueSender.sendCombination(new PrecommitmentQueueDto(getTimeStampFormated(pulse.getTimeStamp()), pulse.getPrecommitmentValue(), pulse.getUri()));
                    Thread.sleep(pulseDelay);
                }
            }

        }
    }

    private void cleanDiscardedNumbers(){
        for (ProcessingErrorDto dto: combineDomainResult.getCombineErrorList()){
            if (dto.getProcessingErrorTypeEnum().equals(ProcessingErrorTypeEnum.DISCARDED_NUMBER)){
                entropyRepository.deleteByTimeStamp(dto.getTimeStamp());
            }
        }
    }
}
