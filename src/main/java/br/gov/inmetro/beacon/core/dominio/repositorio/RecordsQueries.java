package br.gov.inmetro.beacon.core.dominio.repositorio;

import br.gov.inmetro.beacon.api.RecordDto;
import br.gov.inmetro.beacon.core.infra.Record;

import java.util.List;

public interface RecordsQueries {
    RecordDto last();
    RecordDto startChain();
    List<Record> obterTodos();
}
