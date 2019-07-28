package br.gov.inmetro.beacon.v1.domain.repository;

import br.gov.inmetro.beacon.v2.mypackage.infra.PulseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PulsesRepository extends JpaRepository<PulseEntity, Long>, PulsesQueries {
}
