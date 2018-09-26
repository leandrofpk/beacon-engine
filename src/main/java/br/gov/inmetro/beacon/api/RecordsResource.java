package br.gov.inmetro.beacon.api;

import br.gov.inmetro.beacon.core.dominio.repositorio.Records;
import br.gov.inmetro.beacon.core.infra.Record;
import br.gov.inmetro.beacon.core.service.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@RestController
@RequestMapping(value = "/rest/record", produces=MediaType.APPLICATION_XML_VALUE)
public class RecordsResource {

    private Records records;

    @Autowired
    public RecordsResource(Records registros) {
        this.records = registros;
    }

    @RequestMapping("/{timestamp}")
    public RecordDto dataFormatoLong(@PathVariable String timestamp){
        Optional<Record> record = records.findByTimeStamp(longToLocalDateTime(timestamp));

        if (!record.isPresent())
            throw new RecordNotFoundException("TimeStamp:" + timestamp);

        return new RecordDto(record.get());
    }

    @RequestMapping("/last")
    public RecordDto last(){
        return records.lastDto();
    }

    @RequestMapping("/first")
    public RecordDto first(){
        return records.first();
    }

    @RequestMapping("/next/{timestamp}")
    public RecordDto proximo(@PathVariable String timestamp){
        Optional<Record> record = records.findByTimeStamp(longToLocalDateTime(timestamp).plus(1, ChronoUnit.MINUTES));

        if (!record.isPresent())
            throw new RecordNotFoundException("TimeStamp:" + timestamp);

        return new RecordDto(record.get());
    }

    @RequestMapping("/previous/{timestamp}")
    public RecordDto anterior(@PathVariable String timestamp){
        LocalDateTime dateTime = longToLocalDateTime(timestamp).minus(1, ChronoUnit.MINUTES);

        Optional<Record> record = records.findByTimeStamp(dateTime);

        if (!record.isPresent())
            throw new RecordNotFoundException("TimeStamp:" + timestamp);

        return new RecordDto(record.get());
    }

    private LocalDateTime longToLocalDateTime(String timestamp){
        Long millis = new Long(timestamp);
        if (timestamp.length() == 10){
            millis = millis*1000;
        }

        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(new Long(millis)), ZoneId.of("America/Sao_Paulo"));
        return localDateTime;
    }


    @RequestMapping("/index/{pulseIndex}")
    public RecordDto index(@PathVariable String pulseIndex){

//        if (Spring pulseIndex)

        Optional<Record> record = records.findById(new Long(pulseIndex));

        if (!record.isPresent())
            throw new RecordNotFoundException("pulseIndex:" + pulseIndex);

        return new RecordDto(record.get());
    }

}
