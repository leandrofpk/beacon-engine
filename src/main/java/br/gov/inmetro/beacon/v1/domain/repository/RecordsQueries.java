package br.gov.inmetro.beacon.v1.domain.repository;

import br.gov.inmetro.beacon.v1.application.api.RecordDto;
import br.gov.inmetro.beacon.v1.infra.RecordEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RecordsQueries {
    RecordDto lastDto(Integer chain);
    RecordEntity last(Integer chain);
    RecordDto first(Integer chain);
    List<RecordEntity> obterTodos(Integer chain);

    @Deprecated
    Long maxChain(Integer chain);
    Optional<RecordEntity> findByChainAndId(Integer chain, Long idChain);
    Optional<RecordEntity> findByTimeStampWork(Integer chain, LocalDateTime timeStamp);
    Optional<RecordEntity> findByTimeStamp(Integer chain, Long data);
}
