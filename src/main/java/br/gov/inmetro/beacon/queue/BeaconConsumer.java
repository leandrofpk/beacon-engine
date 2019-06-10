package br.gov.inmetro.beacon.queue;

import br.gov.inmetro.beacon.core.dominio.schedule.CombineSourcesService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeaconConsumer {

    private CombineSourcesService combineSourcesService;

    @Autowired
    public BeaconConsumer(CombineSourcesService combineSourcesService) {
        this.combineSourcesService = combineSourcesService;
    }

    @RabbitListener(queues = {"pulses_regular_queue"})
    public void receive(NoiseDto noiseDto) {
        combineSourcesService.addNoise(noiseDto);
    }


}
