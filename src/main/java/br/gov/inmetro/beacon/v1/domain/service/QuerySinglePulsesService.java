package br.gov.inmetro.beacon.v1.domain.service;

import br.gov.inmetro.beacon.v1.domain.repository.PulsesRepository;
import br.gov.inmetro.beacon.v2.mypackage.application.PulseDto;
import br.gov.inmetro.beacon.v2.mypackage.domain.pulse.Pulse;
import br.gov.inmetro.beacon.v2.mypackage.infra.PulseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequestScope
public class QuerySinglePulsesService {

    private final PulsesRepository pulsesRepository;

    @Autowired
    public QuerySinglePulsesService(PulsesRepository records) {
        this.pulsesRepository = records;
    }

    public List<PulseDto> findLast20(Integer chain) {
        List<PulseDto> dtos = new ArrayList<>();
        pulsesRepository.obterTodos(chain).forEach(pulseEntity -> dtos.add(new PulseDto(pulseEntity)));
        return Collections.unmodifiableList(dtos);
    }

    public Optional<PulseEntity> last(Long chain) {
        return Optional.ofNullable(pulsesRepository.last(chain));
    }

    public PulseDto lastDto(Long chain) {
        PulseEntity last = pulsesRepository.last(chain);
        if (last==null){
            return null;
        } else {
            return new PulseDto(last);
        }
    }

    public PulseDto firstDto(Long chain) {
        PulseEntity last = pulsesRepository.first(chain);
        if (last==null){
            return null;
        } else {
            return new PulseDto(last);
        }
    }

    public PulseDto findByTimestamp(ZonedDateTime dateTime){
        PulseEntity byTimestamp = pulsesRepository.findByTimestamp(dateTime);
        if (byTimestamp==null){
            return null;
        } else {
            return new PulseDto(byTimestamp);
        }
    }

}