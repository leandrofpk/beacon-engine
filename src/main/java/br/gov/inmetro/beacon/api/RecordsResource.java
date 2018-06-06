package br.gov.inmetro.beacon.api;

import br.gov.inmetro.beacon.core.dominio.repositorio.Records;
import br.gov.inmetro.beacon.core.infra.Record;
import br.gov.inmetro.beacon.core.service.CadastraRegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/rest/record")
public class RecordsResource {

    private Records records;

    @Autowired
    public RecordsResource(Records registros) {
        this.records = registros;
    }

    @RequestMapping("/{data}")
    public Record dataFormatoLong(@PathVariable String data){
        return records.findByTime(longToLocalDateTime(data));
    }

    @RequestMapping("/last")
    public Record last(){
        return records.last();
    }

    @RequestMapping("/start-chain")
    public Record startChain(){
        return records.startChain();
    }

    @RequestMapping("/next/{data}")
    public Record proximo(@PathVariable String data){
        return records.findByTime(longToLocalDateTime(data).plus(1, ChronoUnit.MINUTES));
    }

    @RequestMapping("/previous/{data}")
    public Record anterior(@PathVariable String data){
        return records.findByTime(longToLocalDateTime(data).minus(1, ChronoUnit.MINUTES));
    }

    private LocalDateTime longToLocalDateTime(String data){
        long millis = new Long(data);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
        return localDateTime;
    }

}
