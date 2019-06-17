package br.gov.inmetro.beacon.core.dominio.schedule;

import br.gov.inmetro.beacon.application.api.RecordSimpleDto;
import br.gov.inmetro.beacon.domain.repository.CombinationErrors;
import br.gov.inmetro.beacon.domain.repository.Records;
import br.gov.inmetro.beacon.domain.service.CadastraRegistroService;
import br.gov.inmetro.beacon.queue.EntropyDto;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final Records records;

    private final CombinationErrors combinationErrors;

    private final int QTD_FONTES = 2;

    private List<EntropyDto> regularNoisesChainOne = new ArrayList<>();

    @Autowired
    public CombineSourcesService(CadastraRegistroService cadastraRegistroService, Records records, CombinationErrors combinationErrors) {
        this.cadastraRegistroService = cadastraRegistroService;
        this.records = records;
        this.combinationErrors = combinationErrors;
    }

    public void addNoise(EntropyDto noiseDto){
        this.regularNoisesChainOne.add(noiseDto);
    }

    public void addNoise(List<EntropyDto> list){
        this.regularNoisesChainOne.addAll(list);
    }

    @Scheduled(cron = "00 * * * * *")
    private void combine () throws Exception {

        // talvez chain of responsabilitty

        // processar sync records
        // verificar necessidade de condicionamento

        CombineDomainService combineDomainService = new CombineDomainService(regularNoisesChainOne, QTD_FONTES, null);
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
