package br.gov.inmetro.beacon.v1.application.api.infra;

import br.gov.inmetro.beacon.v1.application.api.PulseDto;
import br.gov.inmetro.beacon.v1.infra.PulseEntity;
import br.gov.inmetro.beacon.v1.domain.service.RecordNotFoundException;
import br.gov.inmetro.beacon.v1.domain.service.SearchRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@RestController
@RequestScope
@RequestMapping(value = "api/v2/chains/{idChain}/records", produces=MediaType.APPLICATION_JSON_VALUE)
public class ChainsV1Resource {

    private final SearchRecordService searchRecordService;

    @Autowired
    public ChainsV1Resource(SearchRecordService searchRecordService) {
        this.searchRecordService = searchRecordService;
    }

    @RequestMapping("/{timestamp}")
    public PulseDto dataFormatoLong(@PathVariable Integer idChain, @PathVariable String timestamp){
        Optional<PulseEntity> record = searchRecordService.findByUnixTimeStamp(idChain, new Long(timestamp));

        if (!record.isPresent())
            throw new RecordNotFoundException("TimeStamp:" + timestamp);

        return new PulseDto(record.get());
    }

    @RequestMapping("/last")
    public PulseDto last(@PathVariable Integer idChain){
        return searchRecordService.lastDto(idChain);
    }

    @RequestMapping("/first")
    public PulseDto first(@PathVariable Integer idChain){
        return searchRecordService.first(idChain);
    }

    @RequestMapping("/{timestamp}/next")
    public PulseDto proximo(@PathVariable Integer idChain, @PathVariable String timestamp){
        Optional<PulseEntity> record = searchRecordService.findByTimestampWork(idChain, longToLocalDateTime(timestamp).plus(idChain, ChronoUnit.MINUTES).truncatedTo(ChronoUnit.MINUTES));

        if (!record.isPresent())
            throw new RecordNotFoundException("TimeStamp:" + timestamp);

        return new PulseDto(record.get());
    }

    @RequestMapping("/{timestamp}/previous")
    public PulseDto anterior(@PathVariable Integer idChain, @PathVariable String timestamp){
        Optional<PulseEntity> record = searchRecordService.findByTimestampWork(idChain, longToLocalDateTime(timestamp).minus(idChain, ChronoUnit.MINUTES).truncatedTo(ChronoUnit.MINUTES));

        if (!record.isPresent())
            throw new RecordNotFoundException("TimeStamp:" + timestamp);

        return new PulseDto(record.get());
    }

    private LocalDateTime longToLocalDateTime(String timestamp){
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(new Long(timestamp)), ZoneId.of("America/Sao_Paulo"));
        return localDateTime;
    }

    @RequestMapping
    public PulseDto index(@PathVariable Integer idChain, @RequestParam String index){
        Optional<PulseEntity> record = searchRecordService.findByChainAndId(idChain, new Long(index));

        if (!record.isPresent())
            throw new RecordNotFoundException("pulseIndex:" + index);

        return new PulseDto(record.get());
    }

}
