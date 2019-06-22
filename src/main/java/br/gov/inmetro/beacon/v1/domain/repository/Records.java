package br.gov.inmetro.beacon.v1.domain.repository;

import br.gov.inmetro.beacon.v1.infra.RecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Records extends JpaRepository<RecordEntity, Long>, RecordsQueries {
}
