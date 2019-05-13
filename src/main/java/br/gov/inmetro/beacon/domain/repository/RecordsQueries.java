package br.gov.inmetro.beacon.domain.repository;

import br.gov.inmetro.beacon.application.api.RecordDto;
import br.gov.inmetro.beacon.infra.Record;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RecordsQueries {
    RecordDto lastDto(Integer chain);
    Record last(Integer chain);
    RecordDto first(Integer chain);
    List<Record> obterTodos(Integer chain);
    Long maxChain(Integer chain);
    Optional<Record> findByChainAndId(Integer chain, Long idChain);
    Optional<Record> findByTimestamp(Integer chain, LocalDateTime timeStamp);
    Optional<Record> findByUnixTimeStamp(Integer chain, Long data);
}
