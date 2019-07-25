package br.gov.inmetro.beacon.v2.mypackage.domain.chain;

public class ChainDomainService {

    public static ChainValueObject getActiveChain(){
        return new ChainValueObject("2.0",
                                    0,
                                        60000,
                                        1L);
    }

}
