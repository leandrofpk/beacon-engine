package br.gov.inmetro.beacon.v1.domain.repository;

import br.gov.inmetro.beacon.v1.infra.PulseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Pulses extends JpaRepository<PulseEntity, Long>, PulsesQueries {
}
