package br.gov.inmetro.beacon.engine.domain.service;

import br.gov.inmetro.beacon.engine.domain.repository.PulsesRepository;
import br.gov.inmetro.beacon.engine.application.PulseDto;
import br.gov.inmetro.beacon.engine.infra.PulseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuerySequencePulsesService {

    private final PulsesRepository pulsesRepository;

    @Autowired
    public QuerySequencePulsesService(PulsesRepository pulsesRepository) {
        this.pulsesRepository = pulsesRepository;
    }

    public List<PulseDto> sequence(ZonedDateTime timeStamp1, ZonedDateTime timeStamp2){
        long fiveDays = 7200;
        long between = ChronoUnit.MINUTES.between(timeStamp1, timeStamp2);

        if (between > fiveDays){
            throw new BadRequestException("Maximum pulses per request: 7200");
        }

        List<PulseDto> dtos = new ArrayList<>();
        List<PulseEntity> sequence = pulsesRepository.findSequence(timeStamp1, timeStamp2);
        sequence.forEach(entity -> dtos.add(new PulseDto(entity)));
        return dtos;
    }

}
