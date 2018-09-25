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
import java.util.Optional;

@Service
public class CadastraRegistroService {

    private Records records;

    @Autowired
    public CadastraRegistroService(Records records) {
        this.records = records;
    }

    @Transactional
    public void novoRegistro(RecordDto recordDto){

        System.out.println("---------------------------------");
        System.out.println(longToLocalDateTime(recordDto.getTimeStamp()));



        Optional<Record> record = records.findByTimeStamp(longToLocalDateTime(recordDto.getTimeStamp()).truncatedTo(ChronoUnit.MINUTES));

        if (record.isPresent()){
            throw new TimeIsAlreadyRegisteredException("Time already reported");
        }

        Record registroBd = new Record();

        registroBd.setTimeStamp(longToLocalDateTime(recordDto.getTimeStamp()).truncatedTo(ChronoUnit.MINUTES));

        registroBd.setOutputValue(recordDto.getOutputValue());
        registroBd.setVersionBeacon(recordDto.getVersion());
        registroBd.setFrequency(recordDto.getFrequency());
        registroBd.setSignatureValue(recordDto.getSignatureValue());
        registroBd.setPreviousOutputValue(recordDto.getPreviousOutputValue());
        registroBd.setStatusCode(recordDto.getStatusCode());
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
