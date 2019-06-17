package br.gov.inmetro.beacon.domain.repository;

import br.gov.inmetro.beacon.infra.ProcessingErrorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CombinationErrors extends JpaRepository<ProcessingErrorEntity, Long>, CombinationErrorsQueries {
}
