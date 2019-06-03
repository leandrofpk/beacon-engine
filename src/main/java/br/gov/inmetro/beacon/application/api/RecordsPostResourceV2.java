package br.gov.inmetro.beacon.application.api;

import br.gov.inmetro.beacon.domain.service.CadastraRegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/record/v2")
public class RecordsPostResourceV2 {

    private CadastraRegistroService cadastraRegistroService;

    @Autowired
    public RecordsPostResourceV2(CadastraRegistroService cadastraRegistroService) {
        this.cadastraRegistroService = cadastraRegistroService;
    }

    /*
    *  DTO em modo simplificado.  Recebe basicamente os dados brutos
    */
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public  ResponseEntity<?> novo(@Valid @RequestBody RecordSimpleDto recordSimpleDto) throws Exception {
        cadastraRegistroService.novoRegistro(recordSimpleDto);
        return new ResponseEntity<>(recordSimpleDto, HttpStatus.CREATED);
    }

    @PostMapping(value = "sync", consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public  ResponseEntity<?> novo(@Valid @RequestBody List<RecordSimpleDto> recordList) {
        cadastraRegistroService.novoRegistro(recordList);
        return new ResponseEntity<>(recordList, HttpStatus.CREATED);
    }


}
