package br.gov.inmetro.beacon.v1.domain.repository;

import br.gov.inmetro.beacon.v2.mypackage.application.PulseDto;
import br.gov.inmetro.beacon.v2.mypackage.infra.PulseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PulsesQueries {
    PulseDto lastDto(Long chain);
    PulseEntity last(Long chain);
    PulseDto first(Integer chain);
    List<PulseEntity> obterTodos(Integer chain);

    @Deprecated
    Long maxChain(Integer chain);
    Optional<PulseEntity> findByChainAndId(Integer chain, Long idChain);
    Optional<PulseEntity> findByTimeStampWork(Integer chain, LocalDateTime timeStamp);
    Optional<PulseEntity> findByTimeStamp(Integer chain, Long data);
}
