package br.gov.inmetro.beacon.domain.service;

import br.gov.inmetro.beacon.application.api.RecordDto;
import br.gov.inmetro.beacon.application.api.RecordListSimpleDto;
import br.gov.inmetro.beacon.application.api.RecordSimpleDto;
import br.gov.inmetro.beacon.domain.OriginEnum;
import br.gov.inmetro.beacon.domain.RecordDomain;
import br.gov.inmetro.beacon.domain.repository.Records;
import br.gov.inmetro.beacon.infra.DateUtil;
import br.gov.inmetro.beacon.infra.RecordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.util.List;

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
    public void novoRegistro(List<RecordSimpleDto> list) {
        list.forEach(record -> {
            try {
                novoRegistro(record);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

//        records.obterTodos(chain).forEach(record -> dtos.add(new RecordDto(record)));

    }

    @Transactional
    public void novoRegistro(RecordSimpleDto simpleDto) throws Exception {
        RecordEntity lastRecordEntity = records.last(new Integer(simpleDto.getChain()));

        boolean startNweChain = false;
        Long id = 0L;

        if (lastRecordEntity == null){ // se o banco estiver vazio
            startNweChain = true;
        } else {
            id = lastRecordEntity.getId();
        }


        PrivateKey privateKey = CriptoUtilService.loadPrivateKey("privatekey-pkcs8.pem");
        RecordDomain recordDomain = new RecordDomain(simpleDto, lastRecordEntity, env.getProperty("beacon.version"), privateKey,startNweChain);

        RecordEntity newRecord = recordDomain.iniciar();

        newRecord.setChain(simpleDto.getChain());
        newRecord.setIdChain(++id);
        newRecord.setOrigin(OriginEnum.BEACON);
        records.save(newRecord);
    }

    @Deprecated
    @Transactional
    public void novoRegistro(RecordDto recordDto, Integer chain) throws Exception {

        RecordEntity lastRecordEntity = records.last(chain);

        boolean startNewChain = false;

        if (lastRecordEntity != null) {

            final LocalDateTime dateTimeNewRecord =  DateUtil.longToLocalDateTime(recordDto.getTimeStamp());

            if (dateTimeNewRecord.isEqual(lastRecordEntity.getTimeStampWork())) {
                throw new TimeIsAlreadyRegisteredException("Time already reported");
            }

        } else {
            startNewChain = true;
        }

        Long lastChainId = records.maxChain(chain);

        RecordEntity registroBd = new RecordEntity();

        registroBd.setTimeStampWork(DateUtil.longToLocalDateTime(recordDto.getTimeStamp()));
        registroBd.setTimeStamp(new Long(recordDto.getTimeStamp()));

        registroBd.setOutputValue(recordDto.getOutputValue());
        registroBd.setVersionBeacon(env.getProperty("beacon.version"));
        registroBd.setFrequency(recordDto.getFrequency());
        registroBd.setStatusCode(recordDto.getStatusCode());
        registroBd.setSeedValue(recordDto.getRawData());

        if (startNewChain) {
            registroBd.setPreviousOutputValue(CriptoUtilService.hashSha512Hexa("0000000000000000000000000000000000000000000000000000000000000000" +
                    "0000000000000000000000000000000000000000000000000000000000000000"));
        } else {
            registroBd.setPreviousOutputValue(CriptoUtilService.hashSha512Hexa(lastRecordEntity.getSeedValue()));
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

//    @Deprecated
//    private LocalDateTime longToLocalDateTime(String data){
//        Long millis = new Long(data);
//        if (data.length() == 10){
//            millis = millis*1000;
//        }
//
//        // atual
//        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis),
//                ZoneId.of("America/Sao_Paulo")).truncatedTo(ChronoUnit.MINUTES);
//
//        return localDateTime;
//    }


}
