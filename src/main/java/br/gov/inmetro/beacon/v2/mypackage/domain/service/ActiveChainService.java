package br.gov.inmetro.beacon.v2.mypackage.domain.service;

import br.gov.inmetro.beacon.v2.mypackage.domain.chain.ChainValueObject;
import br.gov.inmetro.beacon.v2.mypackage.domain.repository.ChainRepository;
import br.gov.inmetro.beacon.v2.mypackage.infra.ChainEntity;
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
        return new ChainValueObject(entity.getVersion(), entity.getCipherSuite(), entity.getPeriod(), entity.getChainIndex());
    }

}
