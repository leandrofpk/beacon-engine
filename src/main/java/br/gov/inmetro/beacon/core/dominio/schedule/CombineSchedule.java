package br.gov.inmetro.beacon.core.dominio.schedule;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@EnableScheduling
public class CombineSchedule {

    @Scheduled(cron = "10 * * * * *")
    public void teste(){
        System.out.println( LocalDateTime.now());
    }

}
