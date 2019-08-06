package br.gov.inmetro.beacon.v1.application.api.infra;

import br.gov.inmetro.beacon.v1.application.api.RecordDto;
import br.gov.inmetro.beacon.v1.domain.service.RecordNotFoundException;
import br.gov.inmetro.beacon.v1.domain.service.SearchRecordService;
import br.gov.inmetro.beacon.v1.infra.RecordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/v2/chains/{idChain}/records", produces=MediaType.APPLICATION_JSON_VALUE)
public class ChainsResource {

    private final SearchRecordService searchRecordService;

    @Autowired
    public ChainsResource(SearchRecordService searchRecordService) {
        this.searchRecordService = searchRecordService;
    }

    @RequestMapping("/{timestamp}")
    public RecordDto dataFormatoLong(@PathVariable Integer idChain, @PathVariable String timestamp){
        Optional<RecordEntity> record = searchRecordService.findByUnixTimeStamp(idChain, new Long(timestamp));

        if (!record.isPresent())
            throw new RecordNotFoundException("TimeStamp:" + timestamp);

        return new RecordDto(record.get());
    }

    @RequestMapping("/last")
    public RecordDto last(@PathVariable Integer idChain){
        return searchRecordService.lastDto(idChain);
    }

    @RequestMapping("/first")
    public RecordDto first(@PathVariable Integer idChain){
        return searchRecordService.first(idChain);
    }

    @RequestMapping("/{timestamp}/next")
    public RecordDto proximo(@PathVariable Integer idChain, @PathVariable String timestamp){
        Optional<RecordEntity> record = searchRecordService.findByTimestampWork(idChain, longToLocalDateTime(timestamp).plus(idChain, ChronoUnit.MINUTES).truncatedTo(ChronoUnit.MINUTES));

        if (!record.isPresent())
            throw new RecordNotFoundException("TimeStamp:" + timestamp);

        return new RecordDto(record.get());
    }

    @RequestMapping("/{timestamp}/previous")
    public RecordDto anterior(@PathVariable Integer idChain, @PathVariable String timestamp){
        Optional<RecordEntity> record = searchRecordService.findByTimestampWork(idChain, longToLocalDateTime(timestamp).minus(idChain, ChronoUnit.MINUTES).truncatedTo(ChronoUnit.MINUTES));

        if (!record.isPresent())
            throw new RecordNotFoundException("TimeStamp:" + timestamp);

        return new RecordDto(record.get());
    }

    private LocalDateTime longToLocalDateTime(String timestamp){
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(new Long(timestamp)), ZoneId.of("America/Sao_Paulo"));
        return localDateTime;
    }

    @RequestMapping
    public RecordDto index(@PathVariable Integer idChain, @RequestParam String index){
        Optional<RecordEntity> record = searchRecordService.findByChainAndId(idChain, new Long(index));

        if (!record.isPresent())
            throw new RecordNotFoundException("pulseIndex:" + index);

        return new RecordDto(record.get());
    }

}