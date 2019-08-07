package br.gov.inmetro.beacon.v2.mypackage.application;

import br.gov.inmetro.beacon.v1.domain.service.QuerySinglePulsesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "beacon/2.0/chain/{idChain}/pulse", produces= MediaType.APPLICATION_JSON_VALUE)
public class SingleChainResource {

    private final QuerySinglePulsesService singlePulsesService;

    @Autowired
    public SingleChainResource(QuerySinglePulsesService singlePulsesService) {
        this.singlePulsesService = singlePulsesService;
    }

    @RequestMapping("/first")
    public ResponseEntity first(@PathVariable Long idChain){
        PulseDto pulseDto = singlePulsesService.firstDto(idChain);

        if (pulseDto==null){
            return new ResponseEntity("Pulse Not Available.", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(pulseDto, HttpStatus.OK);
    }

    @RequestMapping("/last")
    public ResponseEntity last(@PathVariable Long idChain){
        PulseDto pulseDto = singlePulsesService.lastDto(idChain);

        if (pulseDto==null){
            return new ResponseEntity("Pulse Not Available.", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(pulseDto, HttpStatus.OK);
    }

}
