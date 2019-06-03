package br.gov.inmetro.beacon.domain.service;

import br.gov.inmetro.beacon.application.api.RecordSimpleDto;
import br.gov.inmetro.beacon.domain.OriginEnum;
import br.gov.inmetro.beacon.domain.RecordDomainService;
import br.gov.inmetro.beacon.domain.repository.Records;
import br.gov.inmetro.beacon.infra.RecordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import java.security.PrivateKey;
import java.util.List;

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

        boolean startNewChain = false;
        Long id = 0L;

        if (lastRecordEntity == null){ // se o banco estiver vazio
            startNewChain = true;
        } else {
            id = lastRecordEntity.getId();
        }

        PrivateKey privateKey = CriptoUtilService.loadPrivateKey("privatekey-pkcs8.pem");
        RecordDomainService recordDomainService = new RecordDomainService(simpleDto, lastRecordEntity, env.getProperty("beacon.version"), privateKey,startNewChain);

        RecordNew newRecord = recordDomainService.iniciar();

        RecordEntity recordEntity = new RecordEntity(newRecord, simpleDto.getChain(), ++id);

//        newRecord.setChain(simpleDto.getChain());
//        newRecord.setIdChain(++id);
//        newRecord.setOrigin(OriginEnum.BEACON);
        records.save(recordEntity);
    }

}
