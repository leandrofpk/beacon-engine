package br.gov.inmetro.beacon.v2.mypackage.queue;

import br.gov.inmetro.beacon.v2.mypackage.domain.repository.EntropyRepository;
import br.gov.inmetro.beacon.v2.mypackage.infra.EntropyEntity;
import br.gov.inmetro.beacon.v2.mypackage.scheduling.ReadNewPulseScheduling;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class BeaconConsumer {

    private final ReadNewPulseScheduling readNewPulseScheduling;

    private final EntropyRepository entropyRepository;

    @Autowired
    public BeaconConsumer(ReadNewPulseScheduling combineSourcesService, EntropyRepository entropyRepository) {
        this.readNewPulseScheduling = combineSourcesService;
        this.entropyRepository = entropyRepository;
    }

    @RabbitListener(queues = {"pulses_regular_queue"})
    public void receiveRegular(EntropyDto entropyDto) {
        entropyRepository.save(new EntropyEntity(entropyDto));
    }

    @RabbitListener(queues = {"pulses_sync_queue"})
    public void receiveSync(List<EntropyDto> list) {
        list.forEach(entropyDto -> entropyRepository.save(new EntropyEntity(entropyDto)));
    }

}
