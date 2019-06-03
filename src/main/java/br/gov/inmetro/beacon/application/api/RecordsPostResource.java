package br.gov.inmetro.beacon.application.api;

import br.gov.inmetro.beacon.domain.service.CadastraRegistroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/record")
public class RecordsPostResource {

    private CadastraRegistroService cadastraRegistroService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public RecordsPostResource(CadastraRegistroService cadastraRegistroService) {
        this.cadastraRegistroService = cadastraRegistroService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> novo(RecordDto record) throws Exception {
        log.warn("Post DTO: " + record.toString());
        record.setChain("1");
//        cadastraRegistroService.novoRegistro(record, 1);
        return new ResponseEntity<RecordDto>(HttpStatus.CREATED);
    }

}
