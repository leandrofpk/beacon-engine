package br.gov.inmetro.beacon.queue;

import br.gov.inmetro.beacon.v1.domain.schedule.CombineSourcesService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BeaconConsumer {

    private final CombineSourcesService combineSourcesService;

    @Autowired
    public BeaconConsumer(CombineSourcesService combineSourcesService) {
        this.combineSourcesService = combineSourcesService;
    }

    @RabbitListener(queues = {"pulses2_regular_queue"})
    public void receiveRegular(EntropyDto noiseDto) {
        combineSourcesService.addNoise(noiseDto);
    }

    @RabbitListener(queues = {"pulses2_sync_queue"})
    public void receiveSync(List<EntropyDto> list) {
        combineSourcesService.addNoise(list);
    }

}
