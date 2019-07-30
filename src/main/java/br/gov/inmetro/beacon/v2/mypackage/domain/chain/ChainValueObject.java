package br.gov.inmetro.beacon.v2.mypackage.domain.chain;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class ChainValueObject {

    private final String version;

    private final int cipherSuite;

    private final int period;

    private final long chainIndex;

    public ChainValueObject(@NonNull String version, int cipherSuite,
                            int period, long chainIndex) {
        this.version = version;
        this.cipherSuite = cipherSuite;
        this.period = period;
        this.chainIndex = chainIndex;
    }
}
