package br.gov.inmetro.beacon.domain.service;

import br.gov.inmetro.beacon.application.api.RecordDto;
import br.gov.inmetro.beacon.domain.OriginEnum;
import br.gov.inmetro.beacon.domain.repository.Records;
import br.gov.inmetro.beacon.infra.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import static br.gov.inmetro.beacon.domain.service.CriptoUtilService.sign;

@Service
@RequestScope
public class CadastraRegistroService {

    private Records records;

    private Environment env;

    @Autowired
    public CadastraRegistroService(Records records, Environment env) {
        this.records = records;
        this.env = env;
    }

    @Transactional
    public void novoRegistro(RecordDto recordDto, Integer chain) throws Exception {

        Record lastRecord = records.last(chain);

        boolean startNewChain = false;

        if (lastRecord != null) {

            final LocalDateTime dateTimeNewRecord = longToLocalDateTime(recordDto.getTimeStamp());

            if (dateTimeNewRecord.isEqual(lastRecord.getTimeStamp())) {
                throw new TimeIsAlreadyRegisteredException("Time already reported");
            }

        } else {
            startNewChain = true;
        }

        Long lastChainId = records.maxChain(chain);

        Record registroBd = new Record();

        registroBd.setTimeStamp(longToLocalDateTime(recordDto.getTimeStamp()));

        registroBd.setUnixTimeStamp(new Long(recordDto.getTimeStamp()));

        registroBd.setOutputValue(recordDto.getOutputValue());
        registroBd.setVersionBeacon(env.getProperty("beacon.version"));
        registroBd.setFrequency(recordDto.getFrequency());
        registroBd.setStatusCode(recordDto.getStatusCode());
        registroBd.setSeedValue(recordDto.getRawData());

        if (startNewChain) {
            registroBd.setPreviousOutputValue(CriptoUtilService.hashSha512Hexa("0000000000000000000000000000000000000000000000000000000000000000" +
                    "0000000000000000000000000000000000000000000000000000000000000000"));
        } else {
            registroBd.setPreviousOutputValue(CriptoUtilService.hashSha512Hexa(lastRecord.getSeedValue()));
        }

//        A digital signature (RSA) computed over (in order): version, frequency, timeStamp, seedValue,
//        previousHashValue, errorCode Note: Except for version, the hash is on the byte representations and not the string representations of the data values

        String valueToSign = env.getProperty("beacon.version") + recordDto.getFrequency().getBytes() +
                recordDto.getTimeStamp().getBytes() + recordDto.getSeedValue().getBytes() +
                recordDto.getPreviousOutputValue().getBytes() + "0".getBytes();


        registroBd.setSignatureValue(sign(valueToSign, CriptoUtilService.loadPrivateKey("privatekey-pkcs8.pem")));

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
