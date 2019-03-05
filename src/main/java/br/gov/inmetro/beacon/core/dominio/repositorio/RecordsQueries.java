package br.gov.inmetro.beacon.core.dominio.repositorio;

import br.gov.inmetro.beacon.api.RecordDto;
import br.gov.inmetro.beacon.core.infra.Record;

import java.util.List;

public interface RecordsQueries {
    RecordDto lastDto(Integer chain);
    Record last(Integer chain);
    RecordDto first(Integer chain);
    List<Record> obterTodos(Integer chain);
    Long maxChain(Integer chain);
    Record findByChainAndId(Integer chain, Long idChain);
}
