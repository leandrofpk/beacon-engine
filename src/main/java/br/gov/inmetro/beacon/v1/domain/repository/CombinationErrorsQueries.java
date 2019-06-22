package br.gov.inmetro.beacon.v1.domain.repository;

import br.gov.inmetro.beacon.v1.domain.schedule.ProcessingErrorDto;

import java.util.List;

public interface CombinationErrorsQueries {
    void persist(List<ProcessingErrorDto> list);
}
