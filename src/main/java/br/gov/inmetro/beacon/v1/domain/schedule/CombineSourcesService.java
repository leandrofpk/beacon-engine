package br.gov.inmetro.beacon.v1.domain.schedule;

import br.gov.inmetro.beacon.queue.EntropyDto;
import br.gov.inmetro.beacon.v1.application.api.PulseDto;
import br.gov.inmetro.beacon.v1.application.api.RecordSimpleDto;
import br.gov.inmetro.beacon.v1.domain.repository.CombinationErrors;
import br.gov.inmetro.beacon.v1.domain.repository.Pulses;
import br.gov.inmetro.beacon.v1.domain.service.CadastraRegistroService;
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
public class CombineSourcesService {

    private final CadastraRegistroService cadastraRegistroService;

    private final CombinationErrors combinationErrors;

    private Environment env;

    private Pulses pulses;

    private List<EntropyDto> regularNoises = new ArrayList<>();

    @Autowired
    public CombineSourcesService(CadastraRegistroService cadastraRegistroService, CombinationErrors combinationErrors, Environment env, Pulses pulses) {
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
    private void combine () throws Exception {
        if (regularNoises.isEmpty()){
            return;
        }

        // tem que vir do banco
        final short activeChain = 1;
        // verificar necessidade de condicionamento
        processing(activeChain, env.getProperty("beacon.chain1-number-of-sources"));
    }

    private void processing(int activeChain, String numberOfSources) throws Exception {
        PulseDto lastRecordDto = pulses.lastDto(activeChain);

        CombineDomainService combineDomainService = new CombineDomainService(regularNoises, Integer.toString(activeChain),
                new Integer(numberOfSources), lastRecordDto);
        combineDomainService.processar();

        List<RecordSimpleDto> recordSimpleDtoList = combineDomainService.getRecordSimpleDtoList();
        List<ProcessingErrorDto> combineErrorList = combineDomainService.getCombineErrorList();

        persistir(recordSimpleDtoList, combineErrorList, String.valueOf(activeChain));
    }

    @Transactional
    protected void persistir(List<RecordSimpleDto> recordSimpleDtoList,
                             List<ProcessingErrorDto> combineErrorList, String chain) throws Exception {

        // TODO Talvez mudar para um serviço ou repositorio
        for (RecordSimpleDto recordSimpleDto : recordSimpleDtoList){
            cadastraRegistroService.novoRegistro(recordSimpleDto);

            Predicate<EntropyDto> predicado = noiseDto ->
                    ((noiseDto.getTimeStamp().toString().equals(recordSimpleDto.getTimeStamp())
                            && noiseDto.getChain().equals(chain)));

            // TODO Talvez retirar da transação
            regularNoises.removeIf(predicado);
        }
        combinationErrors.persist(combineErrorList);
    }

}
