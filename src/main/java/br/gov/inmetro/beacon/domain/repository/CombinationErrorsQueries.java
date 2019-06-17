package br.gov.inmetro.beacon.domain.repository;

import br.gov.inmetro.beacon.core.dominio.schedule.ProcessingErrorDto;

import java.util.List;

public interface CombinationErrorsQueries {
    void persist(List<ProcessingErrorDto> list);
}
