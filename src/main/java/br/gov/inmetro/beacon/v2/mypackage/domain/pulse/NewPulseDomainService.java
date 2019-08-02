package br.gov.inmetro.beacon.v2.mypackage.domain.pulse;

import br.gov.inmetro.beacon.v1.application.api.LocalRandomValueDto;
import br.gov.inmetro.beacon.v1.domain.repository.CombinationErrors;
import br.gov.inmetro.beacon.v1.domain.repository.PulsesRepository;
import br.gov.inmetro.beacon.v2.mypackage.application.PulseDto;
import br.gov.inmetro.beacon.v2.mypackage.domain.chain.ChainDomainService;
import br.gov.inmetro.beacon.v2.mypackage.domain.chain.ChainValueObject;
import br.gov.inmetro.beacon.v2.mypackage.infra.PulseEntity;
import br.gov.inmetro.beacon.v2.mypackage.queue.EntropyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewPulseDomainService {

    private final Environment env;

    private final PulsesRepository pulsesRepository;

    private final CombinationErrors combinationErrorsRepository;

    private List<EntropyDto> regularNoises = new ArrayList<>();

    private CombineDomainResult combineDomainResult;

    private PulseDto lastPulseDto;

    private ChainValueObject activeChain;

    private List<Pulse> pulses = new ArrayList<>();

    @Autowired
    public NewPulseDomainService(Environment env, PulsesRepository pulsesRepository, CombinationErrors combinationErrors) {
        this.env = env;
        this.pulsesRepository = pulsesRepository;
        this.combinationErrorsRepository = combinationErrors;
    }

    public void begin(List<EntropyDto> regularNoises){
        this.regularNoises = regularNoises;

        this.activeChain = ChainDomainService.getActiveChain();
        this.lastPulseDto = pulsesRepository.lastDto(activeChain.getChainIndex());
        String property = env.getProperty("beacon.number-of-entropy-sources");

        combinar(activeChain.getChainIndex(), property);
        processar();
        persist();
    }

    private void combinar(long activeChain, String numberOfSources) {
        CombineDomainService combineDomainService = new CombineDomainService(regularNoises, activeChain,
                new Integer(numberOfSources), this.lastPulseDto);
        this.combineDomainResult = combineDomainService.processar();
    }

    private void processar(){
        long vPulseIndex = 0; boolean firstPulse = false;

        if (this.lastPulseDto == null) {  // first pulse
            firstPulse = true;
        }

        for (LocalRandomValueDto localRandomValue: combineDomainResult.getLocalRandomValueDtos()) {
            if (firstPulse){
                this.pulses.add(getFirstPulse(localRandomValue));
                firstPulse = false;
            } else {
                this.pulses.add(getRegularPulse(localRandomValue, ++vPulseIndex));
            }
        }
    }

    private Pulse getRegularPulse(LocalRandomValueDto localRandomValue, long vPulseIndex){
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

        return new Pulse.Builder()
                .setUri(env.getProperty("beacon.url"))
                .setChainValueObject(activeChain)
                .setCertificateId("0")
                .setPulseIndex(++vPulseIndex )
                .setTimeStamp(localRandomValue.getTimeStamp())
                .setLocalRandomValue(localRandomValue.getValue())
                .setListValue(list)
                .setExternal(External.newExternal())
                .setPrecommitmentValue("00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000")
                .setStatusCode(0)
                .setSignatureValue("assinatura")
                .setOutputValue("valor output")
                .build();

    }

    private Pulse getFirstPulse(LocalRandomValueDto localRandomValue){
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

        return new Pulse.Builder()
                .setUri(env.getProperty("beacon.url"))
                .setChainValueObject(activeChain)
                .setCertificateId("0")
                .setPulseIndex(1)
                .setTimeStamp(localRandomValue.getTimeStamp())
                .setLocalRandomValue(localRandomValue.getValue())
                .setListValue(list)
                .setExternal(External.newExternal())
                .setPrecommitmentValue("00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000")
                .setStatusCode(1)
                .setSignatureValue("assinatura")
                .setOutputValue("valor output")
                .build();
    }

    @Transactional
    protected void persist(){
        for (Pulse pulse : pulses){
            pulsesRepository.save(new PulseEntity(pulse));
        }

        combinationErrorsRepository.persist(combineDomainResult.getCombineErrorList());

        combineDomainResult = null;
        pulses.clear();
    }

}
