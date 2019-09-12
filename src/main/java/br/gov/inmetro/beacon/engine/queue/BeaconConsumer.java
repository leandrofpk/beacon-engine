package br.gov.inmetro.beacon.engine.queue;

import br.gov.inmetro.beacon.engine.domain.repository.EntropyRepository;
import br.gov.inmetro.beacon.engine.domain.repository.PrecomRepository;
import br.gov.inmetro.beacon.engine.infra.EntropyEntity;
import br.gov.inmetro.beacon.engine.infra.PrecomEntity;
import br.gov.inmetro.beacon.engine.scheduling.ReadNewPulseScheduling;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@Transactional
public class BeaconConsumer {

    private final ReadNewPulseScheduling readNewPulseScheduling;

    private final EntropyRepository entropyRepository;

    private final PrecomRepository precomRepository;

    private final BeaconVdfQueueSender beaconVdfQueueSender;

    private static final Logger logger = LoggerFactory.getLogger(BeaconConsumer.class);

    @Autowired
    public BeaconConsumer(ReadNewPulseScheduling combineSourcesService, EntropyRepository entropyRepository, PrecomRepository precomRepository, BeaconVdfQueueSender beaconVdfQueueSender) {
        this.readNewPulseScheduling = combineSourcesService;
        this.entropyRepository = entropyRepository;
        this.precomRepository = precomRepository;
        this.beaconVdfQueueSender = beaconVdfQueueSender;
    }

    @RabbitListener(queues = {"pulses_regular_queue"})
    public void receiveRegular(EntropyDto entropyDto) {
        saveEntity(entropyDto);
//        entropyRepository.save(new EntropyEntity(entropyDto));
//        sendToUnicorn(entropyDto);
//        precomRepository.save((new PrecomEntity(entropyDto.getTimeStamp(), entropyDto.getRawData())));
        sendToCombination(entropyDto);
    }

    @RabbitListener(queues = {"pulses_sync_queue"})
    public void receiveSync(List<EntropyDto> list) {
        list.forEach(entropyDto -> entropyRepository.save(new EntropyEntity(entropyDto)));
    }

    @Transactional
    protected void saveEntity(EntropyDto entropyDto){
        entropyRepository.save(new EntropyEntity(entropyDto));
        precomRepository.save((new PrecomEntity(entropyDto.getTimeStamp(), entropyDto.getRawData())));
    }

    private void sendToCombination(EntropyDto entropyDto){
        logger.warn("Current Timestamp: " + ZonedDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        logger.warn("Entropy received:  " + entropyDto);

        long between = ChronoUnit.MINUTES.between(entropyDto.getTimeStamp(), ZonedDateTime.now());
        logger.warn("Difference in minutes:" + between);
        if (between == 0){
            beaconVdfQueueSender.sendCombination(new PrecommitmentQueueDto(entropyDto.getTimeStamp().toString(), entropyDto.getRawData()));
        }
    }

    private void sendToUnicorn(EntropyDto entropyDto){
        beaconVdfQueueSender.sendUnicorn(new PrecommitmentQueueDto(entropyDto.getTimeStamp().toString(), entropyDto.getRawData()));

    }

}
