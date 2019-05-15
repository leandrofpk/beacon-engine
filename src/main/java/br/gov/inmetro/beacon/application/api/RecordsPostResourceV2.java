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

@RestController
@RequestMapping(value = "/rest/record/v2")
public class RecordsPostResourceV2 {

    private CadastraRegistroService cadastraRegistroService;

//    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public RecordsPostResourceV2(CadastraRegistroService cadastraRegistroService) {
        this.cadastraRegistroService = cadastraRegistroService;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public  ResponseEntity<?> novo(@Valid @RequestBody RecordDto recordDto) throws Exception {

        cadastraRegistroService.novoRegistro(recordDto, 2);
        return new ResponseEntity<>(recordDto, HttpStatus.CREATED); //funcionando
    }

}
