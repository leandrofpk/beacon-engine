package br.gov.inmetro.beacon.core.service;

import br.gov.inmetro.beacon.api.RecordDto;
import br.gov.inmetro.beacon.core.dominio.OriginEnum;
import br.gov.inmetro.beacon.core.dominio.repositorio.Records;
import br.gov.inmetro.beacon.core.infra.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Service
public class CadastraRegistroService {

    private Records records;

    @Autowired
    public CadastraRegistroService(Records records) {
        this.records = records;
    }

    @Transactional
    public void novoRegistro(RecordDto record){

        Record recordDb = records.findByTime(longToLocalDateTime(record.getTime()).truncatedTo(ChronoUnit.MINUTES));

        if (recordDb != null){
            throw new TimeIsAlreadyRegistered("Time already reported");
        }

        Record registroBd = new Record();

        registroBd.setTime(longToLocalDateTime(record.getTime()).truncatedTo(ChronoUnit.MINUTES));
        registroBd.setOutputValue(record.getOutputValue());
        registroBd.setVersionBeacon(record.getVersionBeacon());
        registroBd.setSignature(record.getSignature());
        registroBd.setPreviousOutput(record.getPreviousOutput());
        registroBd.setStatus(record.getStatus());
        registroBd.setSeedValue(record.getSeedValue());
        registroBd.setOrigin(OriginEnum.AUTO);

        records.save(registroBd);
    }


    private LocalDateTime longToLocalDateTime(String data){
        long millis = new Long(data);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
        return localDateTime;
    }

}
