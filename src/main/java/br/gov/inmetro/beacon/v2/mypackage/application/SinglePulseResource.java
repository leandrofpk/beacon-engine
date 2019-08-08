package br.gov.inmetro.beacon.v2.mypackage.application;

import br.gov.inmetro.beacon.v1.domain.service.QuerySinglePulsesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RestController
//@Api(value = "Pulse")
@Api(tags = "Pulse Commands", value = "PulseCommands", description = "Controller for Vehicle Commands")
@RequestMapping(value = "beacon/2.0/pulse", produces= MediaType.APPLICATION_JSON_VALUE)
public class SinglePulseResource {

    private final QuerySinglePulsesService singlePulsesService;

    @Autowired
    public SinglePulseResource(QuerySinglePulsesService singlePulsesService) {
        this.singlePulsesService = singlePulsesService;
    }

    @ApiOperation(value = "Pulse at a specific time (or next closest)")
    @RequestMapping("time/{timeStamp}")
    public ResponseEntity specificTime(@PathVariable String timeStamp){
        try {
            ZonedDateTime parse = ZonedDateTime.parse(timeStamp, DateTimeFormatter.ISO_DATE_TIME);
            PulseDto byTimestamp = singlePulsesService.findByTimestamp(parse);

            if (byTimestamp==null){
                return new ResponseEntity("Pulse Not Available.", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(byTimestamp, HttpStatus.OK);

        } catch (Exception e){
            return new ResponseEntity("Bad Request", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("time/next/{timeStamp}")
    public ResponseEntity next(@PathVariable String timeStamp){
        try {
            ZonedDateTime parse = ZonedDateTime.parse(timeStamp, DateTimeFormatter.ISO_DATE_TIME);
            PulseDto byTimestamp = singlePulsesService.findNext(parse);

            if (byTimestamp==null){
                return new ResponseEntity("Pulse Not Available.", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(byTimestamp, HttpStatus.OK);

        } catch (Exception e){
            return new ResponseEntity("Bad Request", HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping("time/previous/{timeStamp}")
    public ResponseEntity previous(@PathVariable String timeStamp){
        try {
            ZonedDateTime parse = ZonedDateTime.parse(timeStamp, DateTimeFormatter.ISO_DATE_TIME);
            PulseDto byTimestamp = singlePulsesService.findPrevious(parse);

            if (byTimestamp==null){
                return new ResponseEntity("Pulse Not Available.", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(byTimestamp, HttpStatus.OK);

        } catch (Exception e){
            return new ResponseEntity("Bad Request", HttpStatus.BAD_REQUEST);
        }

    }

}
