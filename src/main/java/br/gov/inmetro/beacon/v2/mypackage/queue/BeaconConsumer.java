package br.gov.inmetro.beacon.v2.mypackage.queue;

import br.gov.inmetro.beacon.v2.mypackage.scheduling.ReadNewPulseScheduling;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BeaconConsumer {

    private final ReadNewPulseScheduling combineSourcesService;

    @Autowired
    public BeaconConsumer(ReadNewPulseScheduling combineSourcesService) {
        this.combineSourcesService = combineSourcesService;
    }

    @RabbitListener(queues = {"pulses_regular_queue"})
    public void receiveRegular(EntropyDto noiseDto) {
        combineSourcesService.addNoise(noiseDto);
    }

    @RabbitListener(queues = {"pulses_sync_queue"})
    public void receiveSync(List<EntropyDto> list) {
        combineSourcesService.addNoise(list);
    }

}
