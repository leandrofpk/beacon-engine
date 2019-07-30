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
import java.util.Optional;

@Service
public class NewPulseDomainService {

    private final Environment env;

    private final PulsesRepository pulsesRepository;

    private final CombinationErrors combinationErrorsRepository;

    private List<EntropyDto> regularNoises = new ArrayList<>();

    private CombineDomainResult combineDomainResult;

    private Optional<PulseDto> lastPulseDto;

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

        // setar valores iniciais
        this.activeChain = ChainDomainService.getActiveChain();
        this.lastPulseDto = pulsesRepository.lastDto(activeChain.getChainIndex());
        String property = env.getProperty("beacon.number-of-entropy-sources");

        // combinar

        combinar(activeChain.getChainIndex(), property);
        // processar new pulse
        processar();
        // persist new pulse

        persist();
    }

    private void combinar(long activeChain, String numberOfSources) {
        Optional<PulseDto> lastRecordDto = pulsesRepository.lastDto(activeChain);

        CombineDomainService combineDomainService = new CombineDomainService(regularNoises, activeChain,
                new Integer(numberOfSources), lastRecordDto.get());
        this.combineDomainResult = combineDomainService.processar();
    }

    private void processar(){
        long vPulseIndex = 1;

//        List<Pulse> pulses = new ArrayList<>();

        Pulse firstPulse = new Pulse.BuilderRegular()
                .setPrecommitmentValue("00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000")
                .build();

        if (this.lastPulseDto.isPresent()){
            // setar primeiro pulso da cadeia



        } else {
            vPulseIndex = lastPulseDto.get().getPulseIndex();
        }

        for (LocalRandomValueDto localRandomValue: combineDomainResult.getLocalRandomValueDtos()) {

            vPulseIndex = vPulseIndex +1;

            Pulse newPulse = new Pulse.BuilderRegular()
                    .setUri(env.getProperty("beacon.url") +  "/beacon/" + activeChain.getVersion() + "/chain/" + activeChain.getChainIndex() + "/pulse/" + vPulseIndex)
                    .setChainValueObject(activeChain)
                    .setCertificateId("")
                    .setPulseIndex(vPulseIndex)
                    .setTimeStamp(localRandomValue.getTimeStamp())
                    .setLocalRandomValue(localRandomValue.getValue())
                    .setPrecommitmentValue("precommitment")
                    .build();

            this.pulses.add(newPulse);
            System.out.println(newPulse);
        }

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

//    @Transactional
//    protected void persist(List<LocalRandomValueDto> recordSimpleDtoList,
//                           List<ProcessingErrorDto> combineErrorList, String chain) throws Exception {
//
//        // TODO Talvez mudar para um serviço ou repositorio
//        for (LocalRandomValueDto combinedNumber : recordSimpleDtoList){
//            cadastraRegistroService.novoRegistro(combinedNumber);
//
//            Predicate<EntropyDto> predicado = noiseDto ->
//                    ((noiseDto.getTimeStamp().equals(combinedNumber.getTimeStamp())
////                           0 && noiseDto.getChain().equals(chain)));
//                    ));
//
//            // TODO Talvez retirar da transação
//            regularNoises.removeIf(predicado);
//        }
////        combinationErrors.persist(combineErrorList);
//    }

}
