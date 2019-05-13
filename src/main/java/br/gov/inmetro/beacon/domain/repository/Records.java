package br.gov.inmetro.beacon.domain.repository;

import br.gov.inmetro.beacon.infra.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Records extends JpaRepository<Record, Long>, RecordsQueries {
}
