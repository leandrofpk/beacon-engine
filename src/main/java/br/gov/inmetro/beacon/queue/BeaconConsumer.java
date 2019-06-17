package br.gov.inmetro.beacon.queue;

import br.gov.inmetro.beacon.application.api.RecordDto;
import br.gov.inmetro.beacon.core.dominio.schedule.CombineSourcesService;
import br.gov.inmetro.beacon.domain.repository.Records;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class BeaconConsumer {

    private final CombineSourcesService combineSourcesService;

    private final Records records;

    @Autowired
    public BeaconConsumer(CombineSourcesService combineSourcesService, Records records) {
        this.combineSourcesService = combineSourcesService;
        this.records = records;
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
