package br.gov.inmetro.beacon.core.dominio.schedule;

import br.gov.inmetro.beacon.application.api.RecordSimpleDto;
import br.gov.inmetro.beacon.domain.RecordDomainService;
import br.gov.inmetro.beacon.domain.service.CadastraRegistroService;
import br.gov.inmetro.beacon.queue.NoiseDto;
import com.sun.xml.fastinfoset.tools.FI_SAX_Or_XML_SAX_SAXEvent;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
        System.out.println("Iniciar a combinação");
        System.out.println("Qtd noises:" + regularNoises.size());

        CombineDomainService combineDomainService = new CombineDomainService(regularNoises, QTD_FONTES);
        combineDomainService.processar();

        List<RecordSimpleDto> recordSimpleDtoList = combineDomainService.getRecordSimpleDtoList();
        List<CombineErroDto> combineErrorList = combineDomainService.getCombineErrorList();

        persistir(recordSimpleDtoList);
        // gravar os erros

    }

    @Transactional
    protected void persistir(List<RecordSimpleDto> recordSimpleDtoList) throws Exception {
        for (RecordSimpleDto recordSimpleDto : recordSimpleDtoList){
            cadastraRegistroService.novoRegistro(recordSimpleDto);

//            regularNoises.stream().filter()
//            (new NoiseDto(new Long(recordSimpleDto.getTimeStamp()),"1"));
        }
    }


}
