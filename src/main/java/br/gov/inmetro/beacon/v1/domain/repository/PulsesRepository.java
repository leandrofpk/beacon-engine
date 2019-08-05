package br.gov.inmetro.beacon.v1.domain.repository;

import br.gov.inmetro.beacon.v2.mypackage.infra.PulseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Optional;

@Repository
public interface PulsesRepository extends JpaRepository<PulseEntity, Long>, PulsesQueries {
//    Optional<PulseEntity> findByChainAndTimeStamp(Long chain, ZonedDateTime data);
}
