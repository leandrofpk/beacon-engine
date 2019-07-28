package br.gov.inmetro.beacon.v2.mypackage.scheduling;

import br.gov.inmetro.beacon.v2.mypackage.domain.pulse.NewPulseDomainService;
import br.gov.inmetro.beacon.v2.mypackage.queue.EntropyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@EnableScheduling
public class ReadNewPulseScheduling {

    private final NewPulseDomainService newPulseDomainService;

    private List<EntropyDto> regularNoises = new ArrayList<>();

    @Autowired
    public ReadNewPulseScheduling(NewPulseDomainService newPulseDomainService) {
        this.newPulseDomainService = newPulseDomainService;
    }

    public void addNoise(EntropyDto entropyDto){
        this.regularNoises.add(entropyDto);
    }

    public void addNoise(List<EntropyDto> list){
        this.regularNoises.addAll(list);
    }

    @Scheduled(cron = "00 * * * * *")
    private void getNumber() throws Exception {
        if (regularNoises.isEmpty()){
            return;
        }

        newPulseDomainService.begin(regularNoises);

        // tem que vir do banco

//        final long activeChain = 1L;
//        // verificar necessidade de condicionamento
//        processing(activeChain, env.getProperty("beacon.number-of-entropy-sources"));
    }
}
