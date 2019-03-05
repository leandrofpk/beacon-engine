package br.gov.inmetro.beacon.core.service;

import br.gov.inmetro.beacon.api.RecordDto;
import br.gov.inmetro.beacon.core.dominio.repositorio.Records;
import br.gov.inmetro.beacon.core.infra.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.List;

@Component
@RequestScope
public class SearchRecordService {

    private Records records;

    @Autowired
    public SearchRecordService(Records records) {
        this.records = records;
    }

    public List<RecordDto> findLast20(Integer chain){
        List<RecordDto> dtos = new ArrayList<>();
        records.obterTodos(chain).forEach(record -> dtos.add(new RecordDto(record)));
        return dtos;
    }

    public RecordDto last(int chain) {
        return new RecordDto(records.last(chain));
    }

    public Record findByChainAndId(int chain, Long idChain) {
        return records.findByChainAndId(chain, idChain);
    }
}
