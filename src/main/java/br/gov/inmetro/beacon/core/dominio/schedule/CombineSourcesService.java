package br.gov.inmetro.beacon.core.dominio.schedule;

import br.gov.inmetro.beacon.application.api.RecordSimpleDto;
import br.gov.inmetro.beacon.domain.service.CadastraRegistroService;
import br.gov.inmetro.beacon.queue.NoiseDto;
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

    private CadastraRegistroService cadastraRegistroService;

    private final int QTD_FONTES = 2;

    private List<NoiseDto> regularNoises = new ArrayList<>();

    @Autowired
    public CombineSourcesService(CadastraRegistroService cadastraRegistroService) {
        this.cadastraRegistroService = cadastraRegistroService;
    }

    public void addNoise(NoiseDto noiseDto){
        this.regularNoises.add(noiseDto);
    }

    @Scheduled(cron = "00 * * * * *")
    private void combine () throws Exception {
        CombineDomainService combineDomainService = new CombineDomainService(regularNoises, QTD_FONTES);
        combineDomainService.processar();

        List<RecordSimpleDto> recordSimpleDtoList = combineDomainService.getRecordSimpleDtoList();
        List<CombineErroDto> combineErrorList = combineDomainService.getCombineErrorList();

        persistir(recordSimpleDtoList);
        // gravar os erros de combinação

    }

    @Transactional
    protected void persistir(List<RecordSimpleDto> recordSimpleDtoList) throws Exception {
        for (RecordSimpleDto recordSimpleDto : recordSimpleDtoList){
            cadastraRegistroService.novoRegistro(recordSimpleDto);

            Predicate<NoiseDto> predicado = noiseDto ->
                    ((noiseDto.getTimeStamp().toString().equals(recordSimpleDto.getTimeStamp())));

            regularNoises.removeIf(predicado);
        }
    }


}
