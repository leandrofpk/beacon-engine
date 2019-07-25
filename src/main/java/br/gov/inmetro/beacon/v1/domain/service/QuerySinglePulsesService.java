package br.gov.inmetro.beacon.v1.domain.service;

import br.gov.inmetro.beacon.v1.domain.repository.Pulses;
import br.gov.inmetro.beacon.v2.mypackage.application.PulseDto;
import br.gov.inmetro.beacon.v2.mypackage.infra.PulseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequestScope
public class QuerySinglePulsesService {

    private final Pulses pulses;

    @Autowired
    public QuerySinglePulsesService(Pulses records) {
        this.pulses = records;
    }

    public List<PulseDto> findLast20(Integer chain) {
        List<PulseDto> dtos = new ArrayList<>();
        pulses.obterTodos(chain).forEach(pulseEntity -> dtos.add(new PulseDto(pulseEntity)));
        return Collections.unmodifiableList(dtos);
    }

    public Optional<PulseEntity> last(Long chain) {
        return Optional.ofNullable(pulses.last(chain));
    }

    public Optional<PulseEntity> findByChainAndId(int chain, Long idChain) {
        return pulses.findByChainAndId(chain, idChain);
    }

    public Optional<PulseEntity> findByTimestampWork(Integer chain, LocalDateTime timestamp) {
        return pulses.findByTimeStampWork(chain, timestamp);
    }

    public PulseDto lastDto(Long chain) {
        return pulses.lastDto(chain);
    }

    public PulseDto first(Integer chain) {
        return pulses.first(chain);
    }
}