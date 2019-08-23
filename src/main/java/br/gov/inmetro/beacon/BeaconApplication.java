package br.gov.inmetro.beacon;

import br.gov.inmetro.beacon.v2.mypackage.domain.pulse.NewPulseDomainService;
import br.gov.inmetro.beacon.v2.mypackage.queue.EntropyDto;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@SpringBootApplication
@EnableRabbit
public class BeaconApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeaconApplication.class, args);
	}

	@EventListener(ContextRefreshedEvent.class)
	public void contextRefreshedEvent() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

//	@EventListener(ApplicationReadyEvent.class)
//	public void doSomethingAfterStartup() throws Exception {
//		System.out.println("hello world, I have just started up");
//		testSerie();
//	}


}

