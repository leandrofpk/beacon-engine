package br.gov.inmetro.beacon.engine.scheduling;

import br.gov.inmetro.beacon.engine.domain.pulse.NewPulseDomainService;
import br.gov.inmetro.beacon.engine.domain.repository.EntropyRepository;
import br.gov.inmetro.beacon.engine.infra.alerts.SendAlertMailService;
import br.gov.inmetro.beacon.engine.queue.EntropyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
@EnableScheduling
public class ReadNewPulseScheduling {

    private final NewPulseDomainService newPulseDomainService;

    private final EntropyRepository entropyRepository;

    private final SendAlertMailService mailService;

    @Autowired
    public ReadNewPulseScheduling(NewPulseDomainService newPulseDomainService, EntropyRepository entropyRepository, SendAlertMailService mailService) {
        this.newPulseDomainService = newPulseDomainService;
        this.entropyRepository = entropyRepository;
        this.mailService = mailService;
    }

    @Scheduled(cron = "00 * * * * *")
    private void getNumber() throws Exception {

        List<EntropyDto> dtos = new ArrayList<>();

        entropyRepository.findAll().forEach(
                entity -> dtos.add(new EntropyDto(entity.getRawData(),
                                         entity.getPeriod(),
                                         entity.getNoiseSource(),
                                         entity.getTimeStamp(). toString())));

        if (dtos.isEmpty() || dtos.size() == 1){
            ZonedDateTime timeStamp = dtos.get(0).getTimeStamp();

            if (!timeStamp.isEqual(ZonedDateTime.now()
                    .truncatedTo(ChronoUnit.MINUTES)
                    .withZoneSameInstant((ZoneOffset.UTC)
                            .normalized()))) {

            }
            mailService.sendError();
        }

        if (dtos.isEmpty()){
            return;
        }

        newPulseDomainService.begin(dtos);
    }
}
