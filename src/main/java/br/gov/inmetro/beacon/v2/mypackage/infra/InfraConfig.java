package br.gov.inmetro.beacon.v2.mypackage.infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class InfraConfig {

    @Bean
    public AppUri appUri(HttpServletRequest request){
        return new AppUri(request);
    }

}
