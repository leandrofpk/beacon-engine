package br.gov.inmetro.beacon.v2.mypackage.domain.service;

import br.gov.inmetro.beacon.queue.EntropyDto;

public class PulseDomainService {

    private final EntropyDto entropyDto;

    public PulseDomainService(EntropyDto entropyDto) {
        this.entropyDto = entropyDto;
    }

    public void buildPulse(){

    }

}
