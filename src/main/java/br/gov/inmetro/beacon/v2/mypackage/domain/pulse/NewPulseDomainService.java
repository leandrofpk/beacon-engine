package br.gov.inmetro.beacon.v2.mypackage.domain.pulse;

import br.gov.inmetro.beacon.v1.application.api.LocalRandomValueDto;
import br.gov.inmetro.beacon.v1.domain.repository.CombinationErrors;
import br.gov.inmetro.beacon.v1.domain.repository.PulsesRepository;
import br.gov.inmetro.beacon.v1.domain.service.CriptoUtilService;
import br.gov.inmetro.beacon.v2.mypackage.application.PulseDto;
import br.gov.inmetro.beacon.v2.mypackage.domain.chain.ChainDomainService;
import br.gov.inmetro.beacon.v2.mypackage.domain.chain.ChainValueObject;
import br.gov.inmetro.beacon.v2.mypackage.domain.repository.EntropyRepository;
import br.gov.inmetro.beacon.v2.mypackage.domain.service.PastOutputValuesService;
import br.gov.inmetro.beacon.v2.mypackage.infra.PulseEntity;
import br.gov.inmetro.beacon.v2.mypackage.queue.EntropyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    public NewPulseDomainService(Environment env, PulsesRepository pulsesRepository, EntropyRepository entropyRepository,
                                 CombinationErrors combinationErrors, PastOutputValuesService pastOutputValuesService) {
        this.env = env;
        this.pulsesRepository = pulsesRepository;
        this.entropyRepository = entropyRepository;
        this.combinationErrorsRepository = combinationErrors;
        this.pastOutputValuesService = pastOutputValuesService;

    }

    @Transactional
    public void begin(List<EntropyDto> regularNoises) throws Exception {
        this.regularNoises = regularNoises;

        this.activeChain = ChainDomainService.getActiveChain();
        this.lastPulseEntity = pulsesRepository.last(activeChain.getChainIndex());
        String property = env.getProperty("beacon.number-of-entropy-sources");

        combinar(activeChain.getChainIndex(), property);
        processarAndPersistir();
    }

    private void combinar(long activeChain, String numberOfSources) {

        PulseDto pulseDto = null;
        if (lastPulseEntity != null){
            pulseDto = new PulseDto(this.lastPulseEntity);
        }

        CombineDomainService combineDomainService = new CombineDomainService(regularNoises, activeChain,
                new Integer(numberOfSources), pulseDto);
        this.combineDomainResult = combineDomainService.processar();
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

            LocalRandomValueDto localRandomValue1 = localRandomValueDtos.get(currentIndex);
            LocalRandomValueDto localRandomValue2;
            int nextIndex = currentIndex + 1;

            if (nextIndex < localRandomValueDtos.size()){
                localRandomValue2 = localRandomValueDtos.get(nextIndex);
            } else {
                return;
            }

            Pulse pulso;
            if (firstPulseInBd){
                pulso = getFirstPulse(localRandomValue1);
                firstPulseInBd = false;
                previousPulse = pulso;
            } else {
                pulso = getRegularPulse( previousPulse, localRandomValue1, localRandomValue2);
                previousPulse = pulso;
            }
            persistOnePulse(pulso);
//            processedPulses.add(pulso);
        }

    }

    private Pulse getRegularPulse(Pulse previous, LocalRandomValueDto current, LocalRandomValueDto next) throws Exception {
        List<ListValue> list = new ArrayList<>();
        list.add(ListValue.getOneValue(previous.getOutputValue(),
                "previous", previous.getUri()));

        long vPulseIndex = previous.getPulseIndex()+1;
        String uri = env.getProperty("beacon.url") +  "/beacon/" + activeChain.getVersion() + "/chain/" + activeChain.getChainIndex() + "/pulse/" + vPulseIndex;

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
                .setLocalRandomValue(current.getValue())
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

        String uri = env.getProperty("beacon.url") +  "/beacon/" + activeChain.getVersion() + "/chain/" + activeChain.getChainIndex() + "/pulse/" + 1;


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
    protected void persistOnePulse(Pulse pulse) {
        pulsesRepository.save(new PulseEntity(pulse));
        combinationErrorsRepository.persist(combineDomainResult.getCombineErrorList());
        entropyRepository.deleteByTimeStamp(pulse.getTimeStamp());
    }

}
