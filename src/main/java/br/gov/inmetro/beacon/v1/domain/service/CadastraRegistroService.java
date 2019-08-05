package br.gov.inmetro.beacon.v1.domain.service;

import br.gov.inmetro.beacon.v1.application.api.LocalRandomValueDto;
import br.gov.inmetro.beacon.v1.domain.repository.PulsesRepository;
import br.gov.inmetro.beacon.v2.mypackage.infra.PulseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.PrivateKey;

//@Service
public class CadastraRegistroService {

    private PulsesRepository records;

    private Environment env;

    @Autowired
    public CadastraRegistroService(PulsesRepository records, Environment env) {
        this.records = records;
        this.env = env;
    }

    @Transactional
    public void novoRegistro(LocalRandomValueDto combinedNumber) throws Exception {
        PulseEntity lastRecordEntity = records.last(1L);

        boolean startNewChain = false;
        Long id = 0L;

        if (lastRecordEntity == null){ // se o banco estiver vazio
            startNewChain = true;
        } else {
            id = lastRecordEntity.getChainIndex();
        }

        String propertyPrivateKey = env.getProperty("beacon.x509.privatekey");
//        PrivateKey privateKey = CriptoUtilService.loadPrivateKey(propertyPrivateKey);
        PrivateKey privateKey = null;
//        NewPulse newPulse1 = new NewPulse(combinedNumber, lastRecordEntity, privateKey,startNewChain);

//        Pulse newPulse2 = recordDomainService.newPulse();

//        System.out.println("Persistir");
//        System.out.println(newPulse);

//        PulseEntity recordEntity = new PulseEntity(newPulse2);
//        records.save(recordEntity);
    }

}
