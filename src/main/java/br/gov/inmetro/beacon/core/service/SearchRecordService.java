package br.gov.inmetro.beacon.core.service;

import br.gov.inmetro.beacon.api.RecordDto;
import br.gov.inmetro.beacon.core.dominio.repositorio.Records;
import br.gov.inmetro.beacon.core.infra.Record;
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
public class SearchRecordService {

    private Records records;

    @Autowired
    public SearchRecordService(Records records) {
        this.records = records;
    }

    public List<RecordDto> findLast20(Integer chain) {
        List<RecordDto> dtos = new ArrayList<>();
        records.obterTodos(chain).forEach(record -> dtos.add(new RecordDto(record)));
        return Collections.unmodifiableList(dtos);
    }

    public Record last(int chain) {
        return records.last(chain);
    }

    public Optional<Record> findByChainAndId(int chain, Long idChain) {
        return records.findByChainAndId(chain, idChain);
    }

    public Optional<Record> findByTimestamp(Integer chain, LocalDateTime timestamp) {
        return records.findByTimestamp(chain, timestamp);
    }

    public Optional<Record> findByUnixTimeStamp(Integer chain, Long data) {
        return records.findByUnixTimeStamp(chain, data);
    }

    public RecordDto lastDto(Integer chain) {
        return records.lastDto(chain);
    }

    public RecordDto first(Integer chain) {
        return records.first(chain);
    }
}