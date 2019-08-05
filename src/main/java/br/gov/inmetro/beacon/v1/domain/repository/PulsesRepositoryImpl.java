package br.gov.inmetro.beacon.v1.domain.repository;

import br.gov.inmetro.beacon.v2.mypackage.application.PulseDto;
import br.gov.inmetro.beacon.v2.mypackage.domain.pulse.Pulse;
import br.gov.inmetro.beacon.v2.mypackage.infra.PulseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class PulsesRepositoryImpl implements PulsesQueries {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    public PulseDto lastDto(Long chainIndex){
        try {
            PulseEntity r = (PulseEntity) manager.createQuery("from PulseEntity where chainIndex = :chainIndex order by id desc")
                    .setParameter("chainIndex", chainIndex)
                    .setMaxResults(1)
                    .getSingleResult();
            return new PulseDto(r);
        } catch(NoResultException ex){
            return null;
        }
    }

    @Transactional
    public PulseEntity last(Long chainIndex){
        try {
            return (PulseEntity) manager.createQuery("from PulseEntity where chainIndex = :chainIndex order by id desc")
                    .setParameter("chainIndex", chainIndex)
                    .setMaxResults(1)
                    .getSingleResult();

        } catch(NoResultException ex){
            return null;
        }
    }

    @Transactional
    public PulseDto first(Long chainIndex){
        PulseEntity r = (PulseEntity) manager.createQuery("from PulseEntity where chainIndex = :chainIndex order by id")
                .setParameter("chainIndex", chainIndex)
                .setMaxResults(1)
                .getSingleResult();

        return new PulseDto(r);
    }

    @Transactional(readOnly = true)
    public List<PulseEntity> obterTodos(Integer chainIndex){
        return Collections.unmodifiableList(manager
                .createQuery("from PulseEntity where chainIndex = :chainIndex order by id desc")
                .setParameter("chainIndex", chainIndex.toString())
                .setMaxResults(20)
                .getResultList());
    }

    @Transactional(readOnly = true)
    public Optional<PulseEntity> findByChainAndPulseIndex(Long chainIndex, Long pulseIndex){
        PulseEntity recordEntity = (PulseEntity) manager
                .createQuery("from RecordEntity where chainIndex = :chainIndex and pulseIndex = :pulseIndex")
                .setParameter("chainIndex", chainIndex)
                .setParameter("pulseIndex", pulseIndex)
                .getSingleResult();

        return Optional.of(recordEntity);
    }

    @Transactional(readOnly = true)
    public Pulse findOldPulses(Long chainIndex, ZonedDateTime timeStamp){
        PulseEntity pulseEntity = (PulseEntity) manager
                .createQuery("from PulseEntity where chainIndex = :chainIndex and timeStamp >= :timeStamp order by timeStamp")
                .setParameter("chainIndex", chainIndex)
                .setParameter("timeStamp", timeStamp)
                .setMaxResults(1)
                .getSingleResult();
            return Pulse.BuilderFromEntity(pulseEntity);
    }

    //    public Optional<PulseEntity> findByTimeStamp(Integer chainIndex, Long data) {
//        PulseEntity recordEntity = (PulseEntity) manager.createQuery("from PulseEntity r where r.chainIndex = :chainIndex and r.timeStamp = :timestamp")
//                .setParameter("chainIndex", chainIndex.toString())
//                .setParameter("timestamp", data).getSingleResult();
//        return Optional.of(recordEntity);
//    }


}
