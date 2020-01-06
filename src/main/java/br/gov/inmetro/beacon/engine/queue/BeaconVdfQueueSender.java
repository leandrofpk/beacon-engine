package br.gov.inmetro.beacon.engine.queue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class BeaconVdfQueueSender {

    private final RabbitTemplate rabbitTemplate;

    private final Environment env;

    private static final String EXCHANGE = "beacon_pulse_data";

    private static final String ROUTING_KEY_COMBINATION = "pulse.combination";

    private static final String ROUTING_KEY_UNICORN = "pulse.unicorn";

    @Autowired
    public BeaconVdfQueueSender(RabbitTemplate rabbitTemplate, Environment env) {
        this.rabbitTemplate = rabbitTemplate;
        this.env = env;
    }

    @Async
    public void sendCombination(PrecommitmentQueueDto dto) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY_COMBINATION, dto);
        doUnicorn(dto);
    }

    @Deprecated
    private void doUnicorn(PrecommitmentQueueDto dto) {
        boolean isSend = Boolean.parseBoolean(env.getProperty("beacon.unicorn.end-submission"));
        if (!isSend){
            return;
        }

        String minutes = env.getProperty("beacon.unicorn.submission.finalize");
        String[] split = minutes.split(",");

        Integer minute = ZonedDateTime.now().getMinute();

        for (String s : split) {
            if (s.equalsIgnoreCase(minute.toString())){
                sendUnicorn(dto);
            }
        }
    }

    public void sendUnicorn(PrecommitmentQueueDto dto) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY_UNICORN, dto);
    }

}
