package br.gov.inmetro.beacon.engine.domain.repository;

import br.gov.inmetro.beacon.engine.infra.ProcessingErrorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CombinationErrors extends JpaRepository<ProcessingErrorEntity, Long>, CombinationErrorsQueries {
}
