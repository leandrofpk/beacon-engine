package br.gov.inmetro.beacon.application.api;

import br.gov.inmetro.beacon.domain.repository.Records;
import br.gov.inmetro.beacon.domain.service.CadastraRegistroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/rest/record/v2")
public class RecordsPostResourceV2 {

    private CadastraRegistroService cadastraRegistroService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public RecordsPostResourceV2(CadastraRegistroService cadastraRegistroService, Records registros) {
        this.cadastraRegistroService = cadastraRegistroService;
    }

//    @PostMapping
//    public ResponseEntity<?> novo(RecordDto record){
//        log.warn("Post DTO: " + record.toString());
//        cadastraRegistroService.novoRegistro(record);
//        return new ResponseEntity<RecordDto>(HttpStatus.CREATED);
//    }

//    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE)
//    public ResponseEntity<RecordDto> novo(@Valid @RequestBody RecordDto record){
//        RecordDto dto = new RecordDto();
//        cadastraRegistroService.novoRegistro(dto);
//        return new ResponseEntity<RecordDto>(HttpStatus.CREATED);
//    }


    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public  ResponseEntity<?> novo(@Valid @RequestBody RecordDto recordDto){

        cadastraRegistroService.novoRegistro(recordDto, 2);
        return new ResponseEntity<>(recordDto, HttpStatus.CREATED); //funcionando
    }

}
