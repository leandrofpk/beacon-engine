package br.gov.inmetro.beacon.v1.domain.schedule;

import br.gov.inmetro.beacon.v1.application.api.RecordDto;
import br.gov.inmetro.beacon.v1.application.api.RecordSimpleDto;
import br.gov.inmetro.beacon.v1.domain.repository.CombinationErrors;
import br.gov.inmetro.beacon.v1.domain.repository.Records;
import br.gov.inmetro.beacon.v1.domain.service.CadastraRegistroService;
import br.gov.inmetro.beacon.queue.EntropyDto;
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

    private List<EntropyDto> regularNoisesChainOne = new ArrayList<>();

    @Autowired
    public CombineSourcesService(CadastraRegistroService cadastraRegistroService, CombinationErrors combinationErrors, Environment env, Records records) {
        this.cadastraRegistroService = cadastraRegistroService;
        this.combinationErrors = combinationErrors;
        this.env = env;
        this.records = records;
    }

    public void addNoise(EntropyDto noiseDto){
        this.regularNoisesChainOne.add(noiseDto);
    }

    public void addNoise(List<EntropyDto> list){
        this.regularNoisesChainOne.addAll(list);
    }

    @Scheduled(cron = "00 * * * * *")
    private void combine () throws Exception {

        if (regularNoisesChainOne.isEmpty()){
            return;
        }

        // talvez chain of responsabilitty

        // processar sync records
        // verificar necessidade de condicionamento

        RecordDto lastRecordDto = records.lastDto(1);

        CombineDomainService combineDomainService = new CombineDomainService(regularNoisesChainOne, Integer.parseInt(env.getProperty("beacon.number-of-sources")), new Long(lastRecordDto.getTimeStamp()));
        combineDomainService.processar();

        List<RecordSimpleDto> recordSimpleDtoList = combineDomainService.getRecordSimpleDtoList();
        List<ProcessingErrorDto> combineErrorList = combineDomainService.getCombineErrorList();

        persistir(recordSimpleDtoList, combineErrorList);

    }

    @Transactional
    protected void persistir(List<RecordSimpleDto> recordSimpleDtoList,
                             List<ProcessingErrorDto> combineErrorList) throws Exception {

        // TODO Talvez mudar para um serviço ou repositorio
        for (RecordSimpleDto recordSimpleDto : recordSimpleDtoList){
            cadastraRegistroService.novoRegistro(recordSimpleDto);

            Predicate<EntropyDto> predicado = noiseDto ->
                    ((noiseDto.getTimeStamp().toString().equals(recordSimpleDto.getTimeStamp())));

            // TODO Talvez retirar da transação
            regularNoisesChainOne.removeIf(predicado);
        }

        combinationErrors.persist(combineErrorList);

    }

}
