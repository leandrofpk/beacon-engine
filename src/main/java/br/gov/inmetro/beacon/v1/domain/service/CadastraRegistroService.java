package br.gov.inmetro.beacon.v1.domain.service;

import br.gov.inmetro.beacon.v1.application.api.RecordSimpleDto;
import br.gov.inmetro.beacon.v1.domain.RecordDomainService;
import br.gov.inmetro.beacon.v1.domain.repository.Records;
import br.gov.inmetro.beacon.v1.infra.RecordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.PrivateKey;
import java.util.List;

@Service
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

    }

    @Transactional
    public void novoRegistro(RecordSimpleDto simpleDto) throws Exception {
        RecordEntity lastRecordEntity = records.last(new Integer(simpleDto.getChain()));

        boolean startNewChain = false;
        Long id = 0L;

        if (lastRecordEntity == null){ // se o banco estiver vazio
            startNewChain = true;
        } else {
            id = lastRecordEntity.getIdChain();
        }

        PrivateKey privateKey = CriptoUtilService.loadPrivateKey("privatekey-pkcs8.pem");
        RecordDomainService recordDomainService = new RecordDomainService(simpleDto, lastRecordEntity, env.getProperty("beacon.version"), privateKey,startNewChain);

        RecordNew newRecord = recordDomainService.iniciar();

        RecordEntity recordEntity = new RecordEntity(newRecord, simpleDto.getChain(), ++id);

        records.save(recordEntity);
    }

}
