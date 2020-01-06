package br.gov.inmetro.beacon.engine.domain.chain;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class ChainValueObject {

    private final String versionUri;

    private final String versionPulse;

    private final int cipherSuite;

    private final int period;

    private final long chainIndex;

    public ChainValueObject(@NonNull String versionUri, String versionPulse,  int cipherSuite,
                            int period, long chainIndex) {
        this.versionUri = versionUri;
        this.versionPulse = versionPulse;
        this.cipherSuite = cipherSuite;
        this.period = period;
        this.chainIndex = chainIndex;
    }
}
