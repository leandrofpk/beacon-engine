package br.gov.inmetro.beacon.v1.domain.schedule;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DiscardedNumberDto {

    private final Long id;
    private final Long timestamp;
    private final LocalDateTime now;
    private final String chain;

    public DiscardedNumberDto(Long timestamp, LocalDateTime now, String chain) {
        this.id = null;
        this.timestamp = timestamp;
        this.now = now;
        this.chain = chain;
    }
}
