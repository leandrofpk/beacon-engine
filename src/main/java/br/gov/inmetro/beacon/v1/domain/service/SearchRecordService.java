package br.gov.inmetro.beacon.v1.domain.service;

import br.gov.inmetro.beacon.v1.application.api.PulseDto;
import br.gov.inmetro.beacon.v1.domain.repository.Pulses;
import br.gov.inmetro.beacon.v1.infra.PulseEntity;
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

    private Pulses records;

    @Autowired
    public SearchRecordService(Pulses records) {
        this.records = records;
    }

    public List<PulseDto> findLast20(Integer chain) {
        List<PulseDto> dtos = new ArrayList<>();
        records.obterTodos(chain).forEach(record -> dtos.add(new PulseDto(record)));
        return Collections.unmodifiableList(dtos);
    }

    public Optional<PulseEntity> last(int chain) {
        return Optional.ofNullable(records.last(chain));
    }

    public Optional<PulseEntity> findByChainAndId(int chain, Long idChain) {
        return records.findByChainAndId(chain, idChain);
    }

    public Optional<PulseEntity> findByTimestampWork(Integer chain, LocalDateTime timestamp) {
        return records.findByTimeStampWork(chain, timestamp);
    }

    public Optional<PulseEntity> findByUnixTimeStamp(Integer chain, Long data) {
        return records.findByTimeStamp(chain, data);
    }

    public PulseDto lastDto(Integer chain) {
        return records.lastDto(chain);
    }

    public PulseDto first(Integer chain) {
        return records.first(chain);
    }
}