package br.gov.inmetro.beacon.v2.mypackage.domain.repository;

import br.gov.inmetro.beacon.v2.mypackage.infra.PulseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PulseRepository extends JpaRepository<PulseEntity, Long>, PulsesQueries {
}
