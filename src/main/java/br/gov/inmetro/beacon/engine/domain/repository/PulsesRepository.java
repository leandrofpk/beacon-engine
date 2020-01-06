package br.gov.inmetro.beacon.engine.domain.repository;

import br.gov.inmetro.beacon.engine.infra.PulseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface PulsesRepository extends JpaRepository<PulseEntity, Long>, PulsesQueries {

    @Query(value = "SELECT * FROM pulse where pulse.time_stamp > ?1 order by pulse.time_stamp limit 1;", nativeQuery = true)
    PulseEntity findNext(ZonedDateTime timeStamp);

    @Query(value = "SELECT * FROM pulse where pulse.time_stamp < ?1 order by pulse.time_stamp desc limit 1;", nativeQuery = true)
    PulseEntity findPrevious(ZonedDateTime timeStamp);

    @Query(value = "SELECT distinct p from PulseEntity p left join fetch p.listValueEntities l where p.timeStamp >= ?1 and p.timeStamp <=?2 order by p.timeStamp")
    List<PulseEntity> findSequence(ZonedDateTime timeStamp1, ZonedDateTime timeStamp2);
}
