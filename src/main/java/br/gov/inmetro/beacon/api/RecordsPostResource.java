package br.gov.inmetro.beacon.api;

import br.gov.inmetro.beacon.core.dominio.repositorio.Records;
import br.gov.inmetro.beacon.core.infra.Record;
import br.gov.inmetro.beacon.core.service.CadastraRegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/record")
//@RequestMapping(value = "/rest/record", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces=MediaType.APPLICATION_XML_VALUE)
public class RecordsPostResource {

    private CadastraRegistroService cadastraRegistroService;

    @Autowired
    public RecordsPostResource(CadastraRegistroService cadastraRegistroService, Records registros) {
        this.cadastraRegistroService = cadastraRegistroService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces=MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> novo(RecordDto record){


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
