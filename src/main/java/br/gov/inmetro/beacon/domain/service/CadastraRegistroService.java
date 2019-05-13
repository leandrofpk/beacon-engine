package br.gov.inmetro.beacon.domain.service;

import br.gov.inmetro.beacon.application.api.RecordDto;
import br.gov.inmetro.beacon.domain.OriginEnum;
import br.gov.inmetro.beacon.domain.repository.Records;
import br.gov.inmetro.beacon.infra.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Service
@RequestScope
public class CadastraRegistroService {

    private Records records;

    @Autowired
    public CadastraRegistroService(Records records) {
        this.records = records;
    }

    @Transactional
    public void novoRegistro(RecordDto recordDto, Integer chain) {

        Record lastRecord = records.last(chain);

        if (lastRecord != null) {

            final LocalDateTime dateTimeNewRecord = longToLocalDateTime(recordDto.getTimeStamp());

            if (dateTimeNewRecord.isEqual(lastRecord.getTimeStamp())) {
                throw new TimeIsAlreadyRegisteredException("Time already reported");
            }

        }

        Long lastChainId = records.maxChain(chain);

        Record registroBd = new Record();

        registroBd.setTimeStamp(longToLocalDateTime(recordDto.getTimeStamp()));

        registroBd.setUnixTimeStamp(new Long(recordDto.getTimeStamp()));
        registroBd.setChain(lastChainId.toString());


        registroBd.setOutputValue(recordDto.getOutputValue());
        registroBd.setVersionBeacon(recordDto.getVersion());
        registroBd.setFrequency(recordDto.getFrequency());
        registroBd.setSignatureValue(recordDto.getSignatureValue());
        registroBd.setPreviousOutputValue(recordDto.getPreviousOutputValue());
        registroBd.setStatusCode(recordDto.getStatusCode());
        registroBd.setSeedValue(recordDto.getSeedValue());
        registroBd.setOrigin(OriginEnum.BEACON);
        registroBd.setChain(chain.toString());
        registroBd.setIdChain(++lastChainId);

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
