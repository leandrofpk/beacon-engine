package br.gov.inmetro.beacon.v1.domain.schedule;

import br.gov.inmetro.beacon.queue.EntropyDto;
import br.gov.inmetro.beacon.v1.application.api.RecordDto;
import br.gov.inmetro.beacon.v1.application.api.RecordSimpleDto;
import br.gov.inmetro.beacon.v1.domain.repository.CombinationErrors;
import br.gov.inmetro.beacon.v1.domain.repository.Records;
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

    private Records records;

    private List<EntropyDto> regularNoises = new ArrayList<>();

    @Autowired
    public CombineSourcesService(CadastraRegistroService cadastraRegistroService, CombinationErrors combinationErrors, Environment env, Records records) {
        this.cadastraRegistroService = cadastraRegistroService;
        this.combinationErrors = combinationErrors;
        this.env = env;
        this.records = records;
    }

    public void addNoise(EntropyDto noiseDto){
        this.regularNoises.add(noiseDto);
    }

    public void addNoise(List<EntropyDto> list){
        this.regularNoises.addAll(list);
    }

    @Scheduled(cron = "00 * * * * *")
    private void combine () throws Exception {
        if (regularNoises.isEmpty()){
            return;
        }

        // talvez chain of responsability
        // verificar necessidade de condicionamento

        boolean chain1 = Boolean.parseBoolean(env.getProperty("beacon.chain1-enabled"));

        if (chain1){
            processing(1, env.getProperty("beacon.chain1-number-of-sources"));
        }

        boolean chain2 = Boolean.parseBoolean(env.getProperty("beacon.chain2-enabled"));

        if (chain2){
            processing(2, env.getProperty("beacon.chain2-number-of-sources"));
        }

    }

    private void processing(int chain, String numberOfSources) throws Exception {
        RecordDto lastRecordDto = records.lastDto(chain);

        CombineDomainService combineDomainService = new CombineDomainService(regularNoises, Integer.toString(chain),
                new Integer(numberOfSources), lastRecordDto);
        combineDomainService.processar();

        List<RecordSimpleDto> recordSimpleDtoList = combineDomainService.getRecordSimpleDtoList();
        List<ProcessingErrorDto> combineErrorList = combineDomainService.getCombineErrorList();

        persistir(recordSimpleDtoList, combineErrorList, String.valueOf(chain));
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
