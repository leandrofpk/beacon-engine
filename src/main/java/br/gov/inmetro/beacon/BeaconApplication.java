package br.gov.inmetro.beacon;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.TimeZone;

@SpringBootApplication
@EnableRabbit
@EnableAsync
public class BeaconApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeaconApplication.class, args);
	}

	@EventListener(ContextRefreshedEvent.class)
	public void contextRefreshedEvent() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

}

