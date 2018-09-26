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
    public void novoRegistro(RecordDto recordDto) {

        Record lastRecord = records.last();

        if (lastRecord != null) {

            final LocalDateTime dateTimeNewRecord = longToLocalDateTime(recordDto.getTimeStamp());

            if (dateTimeNewRecord.isBefore(lastRecord.getTimeStamp())) {
                throw new TimeIsAlreadyRegisteredException("Invalid time before");
            }

            if (dateTimeNewRecord.isEqual(lastRecord.getTimeStamp())) {
                throw new TimeIsAlreadyRegisteredException("Time already reported");
            }

//            if (dateTimeNewRecord.isAfter(lastRecord.getTimeStamp().plusMinutes(1))) {
//                throw new TimeIsAlreadyRegisteredException("Invalid time future");
//            }
        }
        Record registroBd = new Record();

        registroBd.setTimeStamp(longToLocalDateTime(recordDto.getTimeStamp()));

        registroBd.setUnixTimeStamp(new Long(recordDto.getTimeStamp()));

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

        // atual
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis),
                ZoneId.of("America/Sao_Paulo")).truncatedTo(ChronoUnit.MINUTES);

        return localDateTime;
    }


}
