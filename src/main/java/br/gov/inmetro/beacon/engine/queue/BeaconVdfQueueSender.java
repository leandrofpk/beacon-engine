package br.gov.inmetro.beacon.engine.queue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeaconVdfQueueSender {

    private final RabbitTemplate rabbitTemplate;

    private static final String EXCHANGE = "beacon_pulse_data";

    private static final String ROUTING_KEY_COMBINATION = "pulse.combination";

    private static final String ROUTING_KEY_UNICORN = "pulse.unicorn";

    @Autowired
    public BeaconVdfQueueSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendCombination(PrecommitmentQueueDto dto) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY_COMBINATION, dto);
    }

    public void sendUnicorn(PrecommitmentQueueDto dto) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY_UNICORN, dto);
    }


}
