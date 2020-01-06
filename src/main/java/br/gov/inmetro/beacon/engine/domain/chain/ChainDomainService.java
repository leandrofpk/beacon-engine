package br.gov.inmetro.beacon.engine.domain.chain;

public class ChainDomainService {

    public static ChainValueObject getActiveChain(){
        return new ChainValueObject("2.0",
                                    "Version 2.0",
                                    0,
                                        60000,
                                        1L);
    }

}
