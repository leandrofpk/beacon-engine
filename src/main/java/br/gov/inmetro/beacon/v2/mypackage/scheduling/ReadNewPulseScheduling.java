package br.gov.inmetro.beacon.v2.mypackage.scheduling;

import br.gov.inmetro.beacon.v1.application.api.RecordSimpleDto;
import br.gov.inmetro.beacon.v1.domain.repository.CombinationErrors;
import br.gov.inmetro.beacon.v1.domain.repository.Pulses;
import br.gov.inmetro.beacon.v1.domain.service.CadastraRegistroService;
import br.gov.inmetro.beacon.v2.mypackage.application.PulseDto;
import br.gov.inmetro.beacon.v2.mypackage.domain.pulse.CombineDomainService;
import br.gov.inmetro.beacon.v2.mypackage.domain.pulse.ProcessingErrorDto;
import br.gov.inmetro.beacon.v2.mypackage.queue.EntropyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
@EnableScheduling
public class ReadNewPulseScheduling {

    private final CadastraRegistroService cadastraRegistroService;

    private final CombinationErrors combinationErrors;

    private Environment env;

    private Pulses pulses;

    private List<EntropyDto> regularNoises = new ArrayList<>();

    @Autowired
    public ReadNewPulseScheduling(CadastraRegistroService cadastraRegistroService, CombinationErrors combinationErrors, Environment env, Pulses pulses) {
        this.cadastraRegistroService = cadastraRegistroService;
        this.combinationErrors = combinationErrors;
        this.env = env;
        this.pulses = pulses;
    }

    public void addNoise(EntropyDto entropyDto){
        this.regularNoises.add(entropyDto);
    }

    public void addNoise(List<EntropyDto> list){
        this.regularNoises.addAll(list);
    }

    @Scheduled(cron = "00 * * * * *")
    private void getNumber() throws Exception {
        if (regularNoises.isEmpty()){
            return;
        }

        // tem que vir do banco

        final long activeChain = 1L;
        // verificar necessidade de condicionamento
        processing(activeChain, env.getProperty("beacon.number-of-entropy-sources"));
    }

    private void processing(long activeChain, String numberOfSources) throws Exception {
        PulseDto lastRecordDto = pulses.lastDto(activeChain);

        CombineDomainService combineDomainService = new CombineDomainService(regularNoises, activeChain,
                new Integer(numberOfSources), lastRecordDto);
        combineDomainService.processar();

        List<RecordSimpleDto> recordSimpleDtoList = combineDomainService.getRecordSimpleDtoList();
        List<ProcessingErrorDto> combineErrorList = combineDomainService.getCombineErrorList();

        persist(recordSimpleDtoList, combineErrorList, String.valueOf(activeChain));
    }

//    private void generateNewPulse(){
//    }

    @Transactional
    protected void persist(List<RecordSimpleDto> recordSimpleDtoList,
                           List<ProcessingErrorDto> combineErrorList, String chain) throws Exception {

        // TODO Talvez mudar para um serviço ou repositorio
        for (RecordSimpleDto recordSimpleDto : recordSimpleDtoList){
            cadastraRegistroService.novoRegistro(recordSimpleDto);

            Predicate<EntropyDto> predicado = noiseDto ->
                    ((noiseDto.getTimeStamp().toString().equals(recordSimpleDto.getTimeStamp())
//                           0 && noiseDto.getChain().equals(chain)));
                    ));

            // TODO Talvez retirar da transação
            regularNoises.removeIf(predicado);
        }
        combinationErrors.persist(combineErrorList);
    }

}
