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
    public void novoRegistro(RecordDto recordDto){

        Record recordDb = records.findByTime(longToLocalDateTime(recordDto.getTimeStamp()).truncatedTo(ChronoUnit.MINUTES));
//
        if (recordDb != null){
            throw new TimeIsAlreadyRegistered("Time already reported");
        }

        Record registroBd = new Record();

        registroBd.setTime(longToLocalDateTime(recordDto.getTimeStamp()));

        registroBd.setOutputValue(recordDto.getOutputValue());
        registroBd.setVersionBeacon(recordDto.getVersion());
        registroBd.setSignature(recordDto.getSignatureValue());
        registroBd.setPreviousOutput(recordDto.getPreviousOutputValue());
        registroBd.setStatus(recordDto.getStatusCode());
        registroBd.setSeedValue(recordDto.getSeedValue());
        registroBd.setOrigin(OriginEnum.BEACON);

        records.save(registroBd);
    }

    private LocalDateTime longToLocalDateTime(String data){
        Long millis = new Long(data);
        if (data.length() == 10){
            millis = millis*1000;
        }

        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.of("America/Sao_Paulo"));
        return localDateTime;
    }

}
