package br.gov.inmetro.beacon.engine.queue;

import br.gov.inmetro.beacon.engine.domain.repository.EntropyRepository;
import br.gov.inmetro.beacon.engine.infra.EntropyEntity;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class BeaconConsumer {

    private final EntropyRepository entropyRepository;

    @Autowired
    public BeaconConsumer(EntropyRepository entropyRepository) {
        this.entropyRepository = entropyRepository;
    }

    @RabbitListener(queues = {"pulses_regular_queue"})
    public void receiveRegular(EntropyDto entropyDto) {
        saveEntity(entropyDto);
    }

    @RabbitListener(queues = {"pulses_sync_queue"})
    public void receiveSync(List<EntropyDto> list) {
        list.forEach(entropyDto -> entropyRepository.save(new EntropyEntity(entropyDto)));
    }

    @Transactional
    protected void saveEntity(EntropyDto entropyDto){
        entropyRepository.save(new EntropyEntity(entropyDto));
    }

}
