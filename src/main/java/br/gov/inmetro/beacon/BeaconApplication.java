package br.gov.inmetro.beacon;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class BeaconApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeaconApplication.class, args);
	}

//	@EventListener(ContextRefreshedEvent.class)
//	public void contextRefreshedEvent() {
//		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
//	}

}

