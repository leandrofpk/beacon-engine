package br.gov.inmetro.beacon.v1.domain.repository;

import br.gov.inmetro.beacon.v2.mypackage.domain.pulse.Pulse;
import br.gov.inmetro.beacon.v2.mypackage.infra.PulseEntity;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface PulsesQueries {
    PulseEntity last(Long chain);
    PulseEntity first(Long chain);
    List<PulseEntity> obterTodos(Integer chain);
    Pulse findOldPulses(Long chainIndex, ZonedDateTime timeStamp);
    Optional<PulseEntity> findByChainAndPulseIndex(Long chainIndex, Long pulseIndex);
    PulseEntity findByTimestamp(ZonedDateTime timeStamp);

    //    @Deprecated
//    Long maxChain(Integer chain);
//    Optional<PulseEntity> findByChainAndTimeStamp(Long chain, ZonedDateTime data);
}
