package br.gov.inmetro.beacon.engine.domain.repository;

import br.gov.inmetro.beacon.engine.domain.pulse.Pulse;
import br.gov.inmetro.beacon.engine.infra.PulseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class PulsesRepositoryImpl implements PulsesQueries {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    public PulseEntity last(Long chainIndex){
        Long lastPulseIndex = (Long) manager.createQuery(
                "select max(p.pulseIndex) from PulseEntity p where p.chainIndex = :chainIndex")
                .setParameter("chainIndex", chainIndex)
                .getSingleResult();

        if (lastPulseIndex==null){
            return null;
        } else {
            return findByChainAndPulseIndex(chainIndex, lastPulseIndex);
        }
    }

    @Transactional
    public PulseEntity first(Long chainIndex){
        Long firstPulseIndex = (Long) manager.createQuery(
                "select min(p.pulseIndex) from PulseEntity p where p.chainIndex = :chainIndex")
                .setParameter("chainIndex", chainIndex)
                .getSingleResult();

        if (firstPulseIndex==null){
            return null;
        } else {
            return findByChainAndPulseIndex(chainIndex, firstPulseIndex);
        }
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
    public PulseEntity findByChainAndPulseIndex(Long chainIndex, Long pulseIndex){
        try {
            PulseEntity recordEntity = (PulseEntity) manager
                    .createQuery("from PulseEntity p " +
                            "join fetch p.listValueEntities lve " +
                            "where p.chainIndex = :chainIndex and p.pulseIndex = :pulseIndex")
                    .setParameter("chainIndex", chainIndex)
                    .setParameter("pulseIndex", pulseIndex)
                    .getSingleResult();

            return recordEntity;
        } catch (NoResultException e){
            return null;
        }
    }

    @Transactional(readOnly = true)
    public Pulse findOldPulses(Long chainIndex, ZonedDateTime timeStamp){
        try {
            PulseEntity pulseEntity = (PulseEntity) manager
                    .createQuery("from PulseEntity where chainIndex = :chainIndex and timeStamp >= :timeStamp order by timeStamp")
                    .setParameter("chainIndex", chainIndex)
                    .setParameter("timeStamp", timeStamp)
                    .setMaxResults(1)
                    .getSingleResult();

            return Pulse.BuilderFromEntity(pulseEntity);
        } catch (NoResultException e){
            return null;
        }
    }

    @Transactional(readOnly = true)
    public PulseEntity findByTimestamp(ZonedDateTime timeStamp){
        try {
            return (PulseEntity) manager
                    .createQuery("from PulseEntity p " +
                            "join fetch p.listValueEntities lve " +
                            "where p.timeStamp = :timeStamp")
                    .setParameter("timeStamp", timeStamp)
                    .getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }

    @Transactional(readOnly = true)
    public Optional<PulseEntity> findByChainAndTimestamp(long chainIndex, ZonedDateTime timeStamp){
        PulseEntity pulseEntity = null;
        try {
            pulseEntity = (PulseEntity) manager.createQuery("from PulseEntity p " +
                    "where p.chainIndex = :chainIndex " +
                    "and p.timeStamp = :timeStamp")
                    .setParameter("chainIndex", chainIndex)
                    .setParameter("timeStamp", timeStamp)
                    .getSingleResult();

            return Optional.ofNullable(pulseEntity);
        } catch (NoResultException e){
                return Optional.ofNullable(pulseEntity);
        }
    }
}
