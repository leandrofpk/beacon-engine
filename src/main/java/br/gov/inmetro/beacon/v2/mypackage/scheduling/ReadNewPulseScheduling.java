package br.gov.inmetro.beacon.v2.mypackage.scheduling;

import br.gov.inmetro.beacon.v2.mypackage.domain.pulse.NewPulseDomainService;
import br.gov.inmetro.beacon.v2.mypackage.domain.repository.EntropyRepository;
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

    private final EntropyRepository entropyRepository;

    @Autowired
    public ReadNewPulseScheduling(NewPulseDomainService newPulseDomainService, EntropyRepository entropyRepository) {
        this.newPulseDomainService = newPulseDomainService;
        this.entropyRepository = entropyRepository;
    }

    @Scheduled(cron = "00 * * * * *")
    private void getNumber()  {

        List<EntropyDto> dtos = new ArrayList<>();

        entropyRepository.findAll().forEach(
                entity -> dtos.add(new EntropyDto(entity.getRawData(),
                                         entity.getPeriod(),
                                         entity.getNoiseSource(),
                                         entity.getTimeStamp(). toString())));
        if (dtos.isEmpty()){
            return;
        }
        newPulseDomainService.begin(dtos);
    }
}
