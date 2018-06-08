package br.gov.inmetro.beacon.api;

import br.gov.inmetro.beacon.core.dominio.repositorio.Records;
import br.gov.inmetro.beacon.core.service.CadastraRegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/rest/record", produces=MediaType.APPLICATION_XML_VALUE)
public class RecordsPostResource {

    private CadastraRegistroService cadastraRegistroService;

    @Autowired
    public RecordsPostResource(CadastraRegistroService cadastraRegistroService, Records registros) {
        this.cadastraRegistroService = cadastraRegistroService;
    }

    @PostMapping
    public ResponseEntity<?> novo(@Valid @RequestBody RecordDto record){
        cadastraRegistroService.novoRegistro(record);
        return new ResponseEntity<RecordDto>(HttpStatus.CREATED);
    }

}
