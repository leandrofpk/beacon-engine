package br.gov.inmetro.beacon.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/rest/record")
public class RecordsPostResource {

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void novo(@Valid @RequestBody RecordDto record){

    }

}
