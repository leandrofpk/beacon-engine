package br.gov.inmetro.beacon.v1.domain;

import br.gov.inmetro.beacon.v1.application.api.RecordSimpleDto;
import br.gov.inmetro.beacon.v1.domain.service.CriptoUtilService;
import br.gov.inmetro.beacon.v1.domain.service.RecordNew;
import br.gov.inmetro.beacon.v1.infra.DateUtil;
import br.gov.inmetro.beacon.v1.infra.RecordEntity;

import java.security.PrivateKey;
import java.time.temporal.ChronoUnit;

public class RecordDomainService {

    private RecordSimpleDto recordSimpleDto;

    private RecordEntity lastRecordEntity;

    private String version;

    private PrivateKey privateKey;

    private boolean startNewChain;

    public RecordDomainService(RecordSimpleDto recordSimpleDto, RecordEntity lastRecordEntity,
                               String version, PrivateKey privateKey, boolean startNewChain) {
        this.recordSimpleDto = recordSimpleDto;
        this.lastRecordEntity = lastRecordEntity;
        this.version = version;
        this.privateKey = privateKey;
        this.startNewChain = startNewChain;
    }

    public RecordNew iniciar() throws Exception {
        RecordNew recordNew = new RecordNew();

        //version
        recordNew.setVersion(this.version);

        //frequency
        recordNew.setFrequency(60);

        //timestamp
        recordNew.setTimeStamp(new Long(recordSimpleDto.getTimeStamp()));

        //A seed value represented as a 64 byte (512-bit) hex string value
        recordNew.setSeedValue(recordSimpleDto.getRawData());

        // previousOutputValue
        // The SHA-512 hash value for the previous record - 64 byte hex string
        if (startNewChain){
            recordNew.setPreviousOutputValue("000000000000000000000000000000000000000000000" +
                    "00000000000000000000000000000000000000000000000000000000000000000000000000000000000");
        } else {
            recordNew.setPreviousOutputValue(CriptoUtilService.hashSha512Hexa(lastRecordEntity.getRecordDataString()));
        }

        // statusCode
        // The status code value:
        // 0 - Chain intact, values all good
        // 1 - Start of a new chain of values, previous hash value will be all zeroes
        // 2 - Time between values is greater than the frequency, but the chain is still intact
        recordNew.setStatusCode(validateStatusCode(recordNew));

        // signatureValue
        // A digital signature (RSA) computed over (in order):
        // version, frequency, timeStamp, seedValue, previousHashValue, errorCode
        // Note: Except for version, the hash is on the byte representations and not the
        // string representations of the data values
        recordNew.setSignatureValue(CriptoUtilService.signBytes(recordNew.getRecordInBytes(), privateKey));

        // outputValue
        // The SHA-512 hash of the signatureValue as a 64 byte hex string
        recordNew.setOutputValue(CriptoUtilService.hashSha512Hexa(recordNew.getSignature()));

        return recordNew;
    }

    private String validateStatusCode(RecordNew record){
        if (startNewChain){
            return "1";
        }

        long between = ChronoUnit.MINUTES.between(lastRecordEntity.getTimeStampWork(), DateUtil.longToLocalDateTime( Long.toString(record.getTimeStamp() )));
        if (between == 1){
            return "0";
        }

        return "2";
    }

}
