package br.gov.inmetro.beacon.engine.domain.repository;

import br.gov.inmetro.beacon.engine.domain.pulse.Pulse;
import br.gov.inmetro.beacon.engine.infra.PulseEntity;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface PulsesQueries {
    PulseEntity last(Long chain);
    PulseEntity first(Long chain);
    List<PulseEntity> obterTodos(Integer chain);
    Pulse findOldPulses(Long chainIndex, ZonedDateTime timeStamp);
    PulseEntity findByChainAndPulseIndex(Long chainIndex, Long pulseIndex);
    PulseEntity findByTimestamp(ZonedDateTime timeStamp);
    Optional<PulseEntity> findByChainAndTimestamp(long chainIndex, ZonedDateTime timeStamp);
}
