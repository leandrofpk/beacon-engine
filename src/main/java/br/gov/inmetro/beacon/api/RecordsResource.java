package br.gov.inmetro.beacon.api;

import br.gov.inmetro.beacon.core.dominio.repositorio.Records;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping(value = "/rest/record", produces=MediaType.APPLICATION_XML_VALUE)
public class RecordsResource {

    private Records records;

    @Autowired
    public RecordsResource(Records registros) {
        this.records = registros;
    }

    @RequestMapping("/{data}")
    public RecordDto dataFormatoLong(@PathVariable String data){
        return new RecordDto(records.findByTime(longToLocalDateTime(data)));
    }

    @RequestMapping("/last")
    public RecordDto last(){
        return records.last();
    }

    @RequestMapping("/start-chain")
    public RecordDto startChain(){
        return records.startChain();
    }

    @RequestMapping("/next/{data}")
    public RecordDto proximo(@PathVariable String data){
        return new RecordDto(records.findByTime(longToLocalDateTime(data).plus(1, ChronoUnit.MINUTES)));
    }

    @RequestMapping("/previous/{data}")
    public RecordDto anterior(@PathVariable String data){
        return new RecordDto(records.findByTime(longToLocalDateTime(data).minus(1, ChronoUnit.MINUTES)));
    }

    private LocalDateTime longToLocalDateTime(String data){
        long millis = new Long(data);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
        return localDateTime;
    }

}
