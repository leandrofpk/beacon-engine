package br.gov.inmetro.beacon.v2.mypackage.scheduling;

import br.gov.inmetro.beacon.v2.mypackage.domain.pulse.NewPulseDomainService;
import br.gov.inmetro.beacon.v2.mypackage.domain.repository.EntropyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ReadNewPulseScheduling {

    private final NewPulseDomainService newPulseDomainService;

    private final EntropyRepository entropyRepository;

    @Autowired
    public ReadNewPulseScheduling(NewPulseDomainService newPulseDomainService, EntropyRepository entropyRepository) {
        this.newPulseDomainService = newPulseDomainService;
        this.entropyRepository = entropyRepository;
    }

//    @Scheduled(cron = "00 * * * * *")
//    private void getNumber() throws Exception {
//        if (regularNoises.isEmpty()){
//            return;
//        }
//
//        newPulseDomainService.begin(regularNoises);
//    }
}
