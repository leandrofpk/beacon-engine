package br.gov.inmetro.beacon.v2.mypackage.application;

import br.gov.inmetro.beacon.v2.mypackage.domain.service.QuerySinglePulsesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Chains Commands", value = "ChainCommands", description = "Controller for Chain Commands")
@RestController
@RequestMapping(value = "beacon/2.0/chain/{chainIndex}/pulse", produces= MediaType.APPLICATION_JSON_VALUE)
public class SingleChainResource {

    private final QuerySinglePulsesService singlePulsesService;

    @Autowired
    public SingleChainResource(QuerySinglePulsesService singlePulsesService) {
        this.singlePulsesService = singlePulsesService;
    }

    @ApiOperation(value = "First Pulse of the Chain")
    @GetMapping("/first")
    public ResponseEntity first(@PathVariable Long chainIndex){
        PulseDto pulseDto = singlePulsesService.firstDto(chainIndex);

        if (pulseDto==null){
            return new ResponseEntity("Pulse Not Available.", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(pulseDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Last Pulse of the Chain")
    @GetMapping("/last")
    public ResponseEntity last(@PathVariable Long chainIndex){
        try {
            PulseDto pulseDto = singlePulsesService.lastDto(chainIndex);

            if (pulseDto==null){
                return new ResponseEntity("Pulse Not Available.", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(pulseDto, HttpStatus.OK);

        } catch (Exception e){
            return new ResponseEntity("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{pulseIndex}")
    public ResponseEntity chainAndPulse(@PathVariable Long chainIndex, @PathVariable Long pulseIndex){
        try {
            PulseDto pulseDto = singlePulsesService.findByChainAndPulseIndex(chainIndex, pulseIndex);

            if (pulseDto==null){
                return new ResponseEntity("Pulse Not Available.", HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity(pulseDto, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
