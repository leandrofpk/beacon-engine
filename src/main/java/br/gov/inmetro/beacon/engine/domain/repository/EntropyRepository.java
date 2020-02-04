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

    @Query(value = "SELECT p from EntropyEntity p order by p.timeStamp, p.noiseSource")
    List<EntropyEntity> getOrderedList();
}
