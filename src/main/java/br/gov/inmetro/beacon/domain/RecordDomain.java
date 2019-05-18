package br.gov.inmetro.beacon.domain;

import br.gov.inmetro.beacon.application.api.RecordSimpleDto;
import br.gov.inmetro.beacon.domain.service.CriptoUtilService;
import br.gov.inmetro.beacon.infra.RecordEntity;

import java.security.PrivateKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class RecordDomain {

    private RecordSimpleDto recordSimpleDto;

    private RecordEntity lastRecordEntity;

    private String version;

    private PrivateKey privateKey;

    private boolean startNewChain;

    public RecordDomain(RecordSimpleDto recordSimpleDto, RecordEntity lastRecordEntity,
                        String version, PrivateKey privateKey, boolean startNewChain) {
        this.recordSimpleDto = recordSimpleDto;
        this.lastRecordEntity = lastRecordEntity;
        this.version = version;
        this.privateKey = privateKey;
        this.startNewChain = startNewChain;
    }

    public RecordEntity iniciar() throws Exception {
        RecordEntity newRecordEntity = new RecordEntity();

        //version
        newRecordEntity.setVersionBeacon(this.version);

        //frequency
        newRecordEntity.setFrequency("60");

        //timestamp
        newRecordEntity.setTimeStampWork(longToLocalDateTime(recordSimpleDto.getTimeStamp()));
        newRecordEntity.setTimeStamp(new Long(recordSimpleDto.getTimeStamp()));

        //A seed value represented as a 64 byte (512-bit) hex string value
        newRecordEntity.setSeedValue(recordSimpleDto.getRawData());

        // previousOutputValue
        // The SHA-512 hash value for the previous record - 64 byte hex string

        if (startNewChain){
            newRecordEntity.setPreviousOutputValue("000000000000000000000000000000000000000000000" +
                    "00000000000000000000000000000000000000000000000000000000000000000000000000000000000");
        } else {
            newRecordEntity.setPreviousOutputValue(CriptoUtilService.hashSha512Hexa(lastRecordEntity.getRecordDataString()));
        }

        // statusCode
        // The status code value:
        // 0 - Chain intact, values all good
        // 1 - Start of a new chain of values, previous hash value will be all zeroes
        // 2 - Time between values is greater than the frequency, but the chain is still intact
        newRecordEntity.setStatusCode(validateStatusCode(newRecordEntity));

        // signatureValue
        // A digital signature (RSA) computed over (in order):
        // version, frequency, timeStamp, seedValue, previousHashValue, errorCode
        // Note: Except for version, the hash is on the byte representations and not the
        // string representations of the data values
        newRecordEntity.setSignatureValue(CriptoUtilService.sign(newRecordEntity.getRecordDataBytes(), privateKey));

        System.out.println("Assinatura:" + newRecordEntity.getRecordDataBytes());

        // outputValue
        // The SHA-512 hash of the signatureValue as a 64 byte hex string
        newRecordEntity.setOutputValue(CriptoUtilService.hashSha512Hexa(newRecordEntity.getSignatureValue()));

        return newRecordEntity;
    }


    private String validateStatusCode(RecordEntity newRecordEntity){
        if (startNewChain){
            return "1";
        }

        long between = ChronoUnit.MINUTES.between(lastRecordEntity.getTimeStampWork(), newRecordEntity.getTimeStampWork());
        if (between == 1){
            return "0";
        }

        return "2";
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
