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

//	@Autowired
//	private InfraConfig appUri;
//
//	@Bean
//	public InfraConfig getAppUri(HttpServletRequest request) {
//		String uri = null;
//
//		if (request.getServerPort() != 443){
//			uri = request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
//		} else {
//			uri = request.getServerName() + request.getContextPath();
//		}
//
//		return appUri;
//	}

}

