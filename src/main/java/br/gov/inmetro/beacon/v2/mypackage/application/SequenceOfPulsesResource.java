package br.gov.inmetro.beacon.v2.mypackage.application;

import br.gov.inmetro.beacon.v2.mypackage.domain.service.BadRequestException;
import br.gov.inmetro.beacon.v2.mypackage.domain.service.QuerySequencePulsesService;
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

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@Api(tags = "Sequence Pulse Commands", value = "SequencePulseCommands", description = "Queries for sequences of pulses")
@RequestMapping(value = "beacon/2.0/skiplist", produces= MediaType.APPLICATION_JSON_VALUE)
public class SequenceOfPulsesResource {

    private final QuerySequencePulsesService querySequencePulsesService;

    @Autowired
    public SequenceOfPulsesResource(QuerySequencePulsesService querySequencePulsesService) {
        this.querySequencePulsesService = querySequencePulsesService;
    }

    @ApiOperation(value = "Skiplist between Times")
    @GetMapping("time/{timeStamp1}/{timeStamp2}")
    public ResponseEntity specificTime(@PathVariable String timeStamp1, @PathVariable String timeStamp2){
        try {
            ZonedDateTime parse1 = ZonedDateTime.parse(timeStamp1, DateTimeFormatter.ISO_DATE_TIME);
            ZonedDateTime parse2 = ZonedDateTime.parse(timeStamp2, DateTimeFormatter.ISO_DATE_TIME);

            List<PulseDto> sequence = querySequencePulsesService.sequence(parse1, parse2);

            SkiplistDto skiplist = new SkiplistDto(sequence);

            if (sequence==null){
                return new ResponseEntity("Pulse Not Available.", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(skiplist, HttpStatus.OK);

        } catch (DateTimeParseException e){
            return new ResponseEntity("Bad Request", HttpStatus.BAD_REQUEST);}
        catch (BadRequestException e){
            return new ResponseEntity("Bad Request: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
