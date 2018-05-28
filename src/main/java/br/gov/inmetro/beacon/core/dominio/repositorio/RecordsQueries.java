package br.gov.inmetro.beacon.core.dominio.repositorio;

import br.gov.inmetro.beacon.core.infra.Record;

import java.util.List;

public interface RecordsQueries {
    Record last();
    Record startChain();
    List<Record> obterTodos();
}
