package br.gov.inmetro.beacon.v1.domain.service;

import br.gov.inmetro.beacon.v1.application.api.RecordDto;
import br.gov.inmetro.beacon.v1.domain.repository.Records;
import br.gov.inmetro.beacon.v1.infra.RecordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
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

    public Optional<RecordEntity> last(int chain) {
        return Optional.ofNullable(records.last(chain));
    }

    public Optional<RecordEntity> findByChainAndId(int chain, Long idChain) {
        return records.findByChainAndId(chain, idChain);
    }

    public Optional<RecordEntity> findByTimestampWork(Integer chain, LocalDateTime timestamp) {
        return records.findByTimeStampWork(chain, timestamp);
    }

    public Optional<RecordEntity> findByUnixTimeStamp(Integer chain, Long data) {
        return records.findByTimeStamp(chain, data);
    }

    public RecordDto lastDto(Integer chain) {
        return records.lastDto(chain);
    }

    public RecordDto first(Integer chain) {
        return records.first(chain);
    }
}