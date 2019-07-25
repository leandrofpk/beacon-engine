package br.gov.inmetro.beacon.v1.domain.service;

import br.gov.inmetro.beacon.v1.application.api.RecordSimpleDto;
import br.gov.inmetro.beacon.v1.domain.NewPulseDomainService;
import br.gov.inmetro.beacon.v1.domain.repository.Pulses;
import br.gov.inmetro.beacon.v2.mypackage.domain.pulse.Pulse;
import br.gov.inmetro.beacon.v2.mypackage.infra.PulseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.PrivateKey;

@Service
public class CadastraRegistroService {

    private Pulses records;

    private Environment env;

    @Autowired
    public CadastraRegistroService(Pulses records, Environment env) {
        this.records = records;
        this.env = env;
    }

    @Transactional
    public void novoRegistro(RecordSimpleDto simpleDto) throws Exception {
        PulseEntity lastRecordEntity = records.last(new Long(simpleDto.getChain()));

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
        NewPulseDomainService recordDomainService = new NewPulseDomainService(simpleDto, lastRecordEntity, privateKey,startNewChain);

        Pulse newPulse = recordDomainService.newPulse();

//        System.out.println("Persistir");
//        System.out.println(newPulse);

        PulseEntity recordEntity = new PulseEntity(newPulse);
        records.save(recordEntity);
    }

}
