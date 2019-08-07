package br.gov.inmetro.beacon.v1.domain.repository;

import br.gov.inmetro.beacon.v2.mypackage.domain.pulse.Pulse;
import br.gov.inmetro.beacon.v2.mypackage.infra.PulseEntity;
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
            Optional<PulseEntity> byChainAndPulseIndex = findByChainAndPulseIndex(chainIndex, lastPulseIndex);
            return byChainAndPulseIndex.get();
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
            Optional<PulseEntity> byChainAndPulseIndex = findByChainAndPulseIndex(chainIndex, firstPulseIndex);
            return byChainAndPulseIndex.get();
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
    public Optional<PulseEntity> findByChainAndPulseIndex(Long chainIndex, Long pulseIndex){
        PulseEntity recordEntity = (PulseEntity) manager
                .createQuery("from PulseEntity p " +
                        "join fetch p.listValueEntities lve " +
                        "where p.chainIndex = :chainIndex and p.pulseIndex = :pulseIndex")
                .setParameter("chainIndex", chainIndex)
                .setParameter("pulseIndex", pulseIndex)
                .getSingleResult();

        return Optional.of(recordEntity);
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
            PulseEntity pulseEntity = (PulseEntity) manager
                    .createQuery("from PulseEntity p " +
                            "join fetch p.listValueEntities lve " +
                            "where p.timeStamp = :timeStamp")
                    .setParameter("timeStamp", timeStamp)
                    .getSingleResult();
            return pulseEntity;
        } catch (NoResultException e){
            return null;
        }
    }

    @Override
    public PulseEntity findNext(ZonedDateTime timeStamp) {



        return null;
    }


    //    public Optional<PulseEntity> findByTimeStamp(Integer chainIndex, Long data) {
//        PulseEntity recordEntity = (PulseEntity) manager.createQuery("from PulseEntity r where r.chainIndex = :chainIndex and r.timeStamp = :timestamp")
//                .setParameter("chainIndex", chainIndex.toString())
//                .setParameter("timestamp", data).getSingleResult();
//        return Optional.of(recordEntity);
//    }


}
