package br.gov.inmetro.beacon.engine.domain.repository;

import br.gov.inmetro.beacon.engine.infra.EntropyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;

@Repository
public interface EntropyRepository extends JpaRepository<EntropyEntity, Integer> {
    long deleteByTimeStamp(ZonedDateTime timeStamp);
    EntropyEntity findByTimeStamp(ZonedDateTime timeStamp);
}
