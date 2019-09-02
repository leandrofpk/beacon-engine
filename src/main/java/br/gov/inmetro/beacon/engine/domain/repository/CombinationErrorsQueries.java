package br.gov.inmetro.beacon.engine.domain.repository;

import br.gov.inmetro.beacon.engine.domain.pulse.ProcessingErrorDto;

import java.util.List;

public interface CombinationErrorsQueries {
    void persist(List<ProcessingErrorDto> list);
}
