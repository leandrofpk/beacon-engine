package br.gov.inmetro.beacon.engine.domain.repository;

import br.gov.inmetro.beacon.engine.infra.EntropyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface EntropyRepository extends JpaRepository<EntropyEntity, Integer> {
    long deleteByTimeStamp(ZonedDateTime timeStamp);

    @Query(value = "SELECT e FROM EntropyEntity e ORDER BY e.timeStamp, e.noiseSource")
    List<EntropyEntity> getOrderedList();
}
