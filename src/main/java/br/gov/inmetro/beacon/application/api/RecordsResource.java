package br.gov.inmetro.beacon.application.api;

import br.gov.inmetro.beacon.infra.Record;
import br.gov.inmetro.beacon.domain.service.RecordNotFoundException;
import br.gov.inmetro.beacon.domain.service.SearchRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@RestController
@RequestScope
@RequestMapping(value = "/rest/record", produces=MediaType.APPLICATION_JSON_VALUE)
public class RecordsResource {

    private final SearchRecordService searchRecordService;

    @Autowired
    public RecordsResource(SearchRecordService searchRecordService) {
        this.searchRecordService = searchRecordService;
    }

    @RequestMapping("/{timestamp}")
    public RecordDto dataFormatoLong(@PathVariable String timestamp){
        Optional<Record> record = searchRecordService.findByUnixTimeStamp(1, new Long(timestamp));

        if (!record.isPresent())
            throw new RecordNotFoundException("TimeStamp:" + timestamp);

        return new RecordDto(record.get());
    }

    @RequestMapping("/last")
    public RecordDto last(){
        return searchRecordService.lastDto(1);
    }

    @RequestMapping("/first")
    public RecordDto first(){
        return searchRecordService.first(1);
    }

    @RequestMapping("/next/{timestamp}")
    public RecordDto proximo(@PathVariable String timestamp){
        Optional<Record> record = searchRecordService.findByTimestamp(1, longToLocalDateTime(timestamp).plus(1, ChronoUnit.MINUTES).truncatedTo(ChronoUnit.MINUTES));

        if (!record.isPresent())
            throw new RecordNotFoundException("TimeStamp:" + timestamp);

        return new RecordDto(record.get());
    }

    @RequestMapping("/previous/{timestamp}")
    public RecordDto anterior(@PathVariable String timestamp){
        Optional<Record> record = searchRecordService.findByTimestamp(1, longToLocalDateTime(timestamp).minus(1, ChronoUnit.MINUTES).truncatedTo(ChronoUnit.MINUTES));

        if (!record.isPresent())
            throw new RecordNotFoundException("TimeStamp:" + timestamp);

        return new RecordDto(record.get());
    }

    private LocalDateTime longToLocalDateTime(String timestamp){
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(new Long(timestamp)), ZoneId.of("America/Sao_Paulo"));
        return localDateTime;
    }


    @RequestMapping("/index/{pulseIndex}")
    public RecordDto index(@PathVariable String pulseIndex){
        Optional<Record> record = searchRecordService.findByChainAndId(1, new Long(pulseIndex));

        if (!record.isPresent())
            throw new RecordNotFoundException("pulseIndex:" + pulseIndex);

        return new RecordDto(record.get());
    }

}
