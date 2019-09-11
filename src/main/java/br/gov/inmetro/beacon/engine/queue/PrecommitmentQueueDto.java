package br.gov.inmetro.beacon.engine.queue;

import lombok.Getter;

/*
    DTO used to be transmitted via rabbitmq
 */
@Getter
public class PrecommitmentQueueDto {

    private final String timeStamp;

    private final String precommitment;

    public PrecommitmentQueueDto(String timeStamp, String precommitment) {
        this.timeStamp = timeStamp;
        this.precommitment = precommitment;
    }
}
