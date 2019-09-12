package br.gov.inmetro.beacon.engine.domain.repository;

import br.gov.inmetro.beacon.engine.infra.PrecomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;

@Repository
public interface PrecomRepository extends JpaRepository<PrecomEntity, Long> {
    long deleteByTimeStamp(ZonedDateTime timeStamp);
    PrecomEntity findByTimeStamp(ZonedDateTime timeStamp);
}
