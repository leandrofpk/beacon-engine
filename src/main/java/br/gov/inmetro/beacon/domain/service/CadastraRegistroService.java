package br.gov.inmetro.beacon.domain.service;

import br.gov.inmetro.beacon.application.api.RecordDto;
import br.gov.inmetro.beacon.domain.OriginEnum;
import br.gov.inmetro.beacon.domain.repository.Records;
import br.gov.inmetro.beacon.infra.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import java.security.NoSuchAlgorithmException;
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
    public void novoRegistro(RecordDto recordDto, Integer chain) throws NoSuchAlgorithmException {

        Record lastRecord = records.last(chain);

//        final CriptoUtilService criptoUtilService = new CriptoUtilService();

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

        registroBd.setOutputValue(recordDto.getOutputValue());
        registroBd.setVersionBeacon(recordDto.getVersion());
        registroBd.setFrequency(recordDto.getFrequency());
        registroBd.setStatusCode(recordDto.getStatusCode());
        registroBd.setSeedValue(recordDto.getSeedValue());
        registroBd.setPreviousOutputValue(CriptoUtilService.hashSha512Hexa(lastRecord.getSeedValue()));

//        A digital signature (RSA) computed over (in order): version, frequency, timeStamp, seedValue,
//        previousHashValue, errorCode Note: Except for version, the hash is on the byte representations and not the string representations of the data values

        String valueToSign = recordDto.getVersion() + recordDto.getFrequency().getBytes() +
                recordDto.getTimeStamp().getBytes() + recordDto.getSeedValue().getBytes() +
                recordDto.getPreviousOutputValue().getBytes() + "0".getBytes();

//        registroBd.setSignatureValue(CriptoUtilService.sign(valueToSign. ) );

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
