package br.gov.inmetro.beacon.api;

import br.gov.inmetro.beacon.core.dominio.repositorio.Records;
import br.gov.inmetro.beacon.core.service.CadastraRegistroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rest/record")
public class RecordsPostResource {

    private CadastraRegistroService cadastraRegistroService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public RecordsPostResource(CadastraRegistroService cadastraRegistroService, Records registros) {
        this.cadastraRegistroService = cadastraRegistroService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces=MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> novo(RecordDto record){
        log.error("Post DTO: " + record.toString());
        cadastraRegistroService.novoRegistro(record);
        return new ResponseEntity<RecordDto>(HttpStatus.CREATED);
    }

//    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE)
//    public ResponseEntity<Record> novo(@Valid @RequestBody Record record){
//        RecordDto dto = new RecordDto();
//        cadastraRegistroService.novoRegistro(dto);
//        return new ResponseEntity<Record>(HttpStatus.CREATED);
//    }

//    @RequestMapping(method = RequestMethod.POST,
//            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
//    @ResponseStatus(HttpStatus.CREATED)
//    public void handleFormRequest(@RequestBody MultiValueMap<String, String> formParams) {
//        System.out.println("form params received " + formParams);
//
//        final Map<String, String> stringStringMap = formParams.toSingleValueMap();
//
//
//    }



}
