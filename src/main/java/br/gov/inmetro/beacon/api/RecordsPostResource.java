package br.gov.inmetro.beacon.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.transform.Result;


@RestController
@RequestMapping("/rest/record")
public class RecordsPostResource {

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public  ResponseEntity<?> novo(@Valid @RequestBody RecordDto record, Errors errors){
        System.out.println("------------------------------------");
        System.out.println(record);

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
//
        }

        return new ResponseEntity<RecordDto>(record, HttpStatus.OK);
    }

}
