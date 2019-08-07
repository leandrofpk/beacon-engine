package br.gov.inmetro.beacon;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestFlywayConfig {
    @Bean
    public FlywayMigrationStrategy clean() {
        return flyway -> {
            flyway.clean();
            flyway.migrate();
        };
    }
}
