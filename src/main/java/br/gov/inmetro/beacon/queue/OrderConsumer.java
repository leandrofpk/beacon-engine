package br.gov.inmetro.beacon.queue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    @RabbitListener(queues = {"${queue.order.name}"})
    public void receive(@Payload String fileBody) {
//        log.info("Order: " + order);
        System.out.println(fileBody);
    }
}
