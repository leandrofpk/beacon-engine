package br.gov.inmetro.beacon.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/record")
public class RecordsPostResource {

    @PostMapping
    public void novo(){

    }

}
