package br.gov.inmetro.beacon.v1.domain.repository;

import br.gov.inmetro.beacon.v2.mypackage.domain.pulse.ProcessingErrorDto;

import java.util.List;

public interface CombinationErrorsQueries {
    void persist(List<ProcessingErrorDto> list);
}
