package br.gov.inmetro.beacon.v2.mypackage.domain.repository;

import br.gov.inmetro.beacon.v2.mypackage.infra.EntropyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;

@Repository
public interface EntropyRepository extends JpaRepository<EntropyEntity, Integer> {
    long deleteByTimeStamp(ZonedDateTime timeStamp);
}
