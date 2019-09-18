package br.gov.inmetro.beacon.engine.domain.service;

import br.gov.inmetro.beacon.engine.domain.chain.ChainValueObject;
import br.gov.inmetro.beacon.engine.domain.repository.ChainRepository;
import br.gov.inmetro.beacon.engine.infra.ChainEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActiveChainService {

    private final ChainRepository chainRepository;

    @Autowired
    public ActiveChainService(ChainRepository chainRepository) {
        this.chainRepository = chainRepository;
    }

    public ChainValueObject get(){
        ChainEntity entity = chainRepository.findTop1ByActive(true);
        return new ChainValueObject(entity.getVersionUri(),entity.getVersionPulse(), entity.getCipherSuite(), entity.getPeriod(), entity.getChainIndex());
    }

}
