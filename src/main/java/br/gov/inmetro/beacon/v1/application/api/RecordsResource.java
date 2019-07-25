package br.gov.inmetro.beacon.v1.application.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/record", produces=MediaType.APPLICATION_JSON_VALUE)
public class RecordsResource {

//    private final SearchRecordService searchRecordService;
//
//    @Autowired
//    public RecordsResource(SearchRecordService searchRecordService) {
//        this.searchRecordService = searchRecordService;
//    }
//
//    @RequestMapping("/{timestamp}")
//    public PulseDto dataFormatoLong(@PathVariable String timestamp){
//        Optional<PulseEntity> record = searchRecordService.findByUnixTimeStamp(1, new Long(timestamp));
//
//        if (!record.isPresent())
//            throw new RecordNotFoundException("TimeStamp:" + timestamp);
//
//        return new PulseDto(record.get());
//    }
//
//    @RequestMapping("/last")
//    public PulseDto last(){
//        return searchRecordService.lastDto(1);
//    }
//
//    @RequestMapping("/first")
//    public PulseDto first(){
//        return searchRecordService.first(1);
//    }
//
//    @RequestMapping("/next/{timestamp}")
//    public PulseDto proximo(@PathVariable String timestamp){
//        Optional<PulseEntity> record = searchRecordService.findByTimestampWork(1, longToLocalDateTime(timestamp).plus(1, ChronoUnit.MINUTES).truncatedTo(ChronoUnit.MINUTES));
//
//        if (!record.isPresent())
//            throw new RecordNotFoundException("TimeStamp:" + timestamp);
//
//        return new PulseDto(record.get());
//    }
//
//    @RequestMapping("/previous/{timestamp}")
//    public PulseDto anterior(@PathVariable String timestamp){
//        Optional<PulseEntity> record = searchRecordService.findByTimestampWork(1, longToLocalDateTime(timestamp).minus(1, ChronoUnit.MINUTES).truncatedTo(ChronoUnit.MINUTES));
//
//        if (!record.isPresent())
//            throw new RecordNotFoundException("TimeStamp:" + timestamp);
//
//        return new PulseDto(record.get());
//    }
//
//    private LocalDateTime longToLocalDateTime(String timestamp){
//        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(new Long(timestamp)), ZoneId.of("America/Sao_Paulo"));
//        return localDateTime;
//    }
//
//
//    @RequestMapping("/index/{pulseIndex}")
//    public PulseDto index(@PathVariable String pulseIndex){
//        Optional<PulseEntity> record = searchRecordService.findByChainAndId(1, new Long(pulseIndex));
//
//        if (!record.isPresent())
//            throw new RecordNotFoundException("pulseIndex:" + pulseIndex);
//
//        return new PulseDto(record.get());
//    }

}
