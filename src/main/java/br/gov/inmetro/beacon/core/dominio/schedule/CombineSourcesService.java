package br.gov.inmetro.beacon.core.dominio.schedule;

import br.gov.inmetro.beacon.application.api.RecordSimpleDto;
import br.gov.inmetro.beacon.domain.RecordDomainService;
import br.gov.inmetro.beacon.queue.NoiseDto;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@EnableScheduling
public class CombineSourcesService {

    private final int QTD_FONTES = 2;

    private List<NoiseDto> regularNoises = new ArrayList<>();

    public void addNoise(NoiseDto noiseDto){
        this.regularNoises.add(noiseDto);
    }

    @Scheduled(cron = "00 * * * * *")
    private void combine (){
        System.out.println("Iniciar a combinação");
        System.out.println("Qtd noises:" + regularNoises.size());

        CombineDomainService combineDomainService = new CombineDomainService(regularNoises, QTD_FONTES);
        combineDomainService.processar();

        List<RecordSimpleDto> recordSimpleDtoList = combineDomainService.getRecordSimpleDtoList();
        List<CombineErroDto> combineErrorList = combineDomainService.getCombineErrorList();

//        new RecordDomainService();


//        teste();

    }


}
