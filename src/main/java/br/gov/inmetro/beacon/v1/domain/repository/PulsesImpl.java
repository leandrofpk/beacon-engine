package br.gov.inmetro.beacon.v1.domain.repository;

import br.gov.inmetro.beacon.v1.application.api.PulseDto;
import br.gov.inmetro.beacon.v1.infra.PulseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class PulsesImpl implements PulsesQueries {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    public br.gov.inmetro.beacon.v2.mypackage.application.PulseDto lastDto(Integer chain){
        try {
            PulseEntity r = (PulseEntity) manager.createQuery("from PulseEntity where chain = :chain order by id desc")
                    .setParameter("chain", chain.toString())
                    .setMaxResults(1)
                    .getSingleResult();
            return new br.gov.inmetro.beacon.v2.mypackage.application.PulseDto(r);
        } catch(NoResultException ex){
            return null;
        }
    }

    @Transactional
    public PulseEntity last(Integer chain){
        try {
            return (PulseEntity) manager.createQuery("from RecordEntity where chain = :chain order by id desc")
                    .setParameter("chain", chain.toString())
                    .setMaxResults(1)
                    .getSingleResult();

        } catch(NoResultException ex){
            return null;
        }
    }

    @Transactional
    public PulseDto first(Integer chain){
        PulseEntity r = (PulseEntity) manager.createQuery("from RecordEntity where chain = :chain order by id")
                .setParameter("chain", chain.toString())
                .setMaxResults(1)
                .getSingleResult();

        return new PulseDto(r);

    }

    @Transactional(readOnly = true)
    public List<PulseEntity> obterTodos(Integer chain){
        return Collections.unmodifiableList(manager
                .createQuery("from RecordEntity where chain = :chain order by id desc")
                .setParameter("chain", chain.toString())
                .setMaxResults(20)
                .getResultList());
    }

    @Transactional(readOnly = true)
    public Long maxChain(Integer chain){

        Long singleResult = (Long) manager.createQuery(" select max(r.idChain) from RecordEntity r where r.chain = :chain ")
                .setParameter("chain", chain.toString())
                .getSingleResult();

        return singleResult == null ? 0 : singleResult;
    }

    @Transactional(readOnly = true)
    public Optional<PulseEntity> findByTimeStampWork(Integer chain, LocalDateTime timeStamp){

        PulseEntity recordEntity;

        try {

            recordEntity = (PulseEntity) manager.createQuery("from RecordEntity r " +
                    "where r.chain = :chain and r.timeStampWork = :timeStamp")
                    .setParameter("chain", chain.toString())
                    .setParameter("timeStamp", timeStamp).getSingleResult();
        } catch (NoResultException e){
            return Optional.empty();
        }

        return Optional.of(recordEntity);
    }

    @Override
    public Optional<PulseEntity> findByTimeStamp(Integer chain, Long data) {
        PulseEntity recordEntity = (PulseEntity) manager.createQuery("from RecordEntity r where r.chain = :chain and r.timeStamp = :timestamp")
                .setParameter("chain", chain.toString())
                .setParameter("timestamp", data).getSingleResult();
        return Optional.of(recordEntity);
    }

    @Transactional(readOnly = true)
    public Optional<PulseEntity> findByChainAndId(Integer chain, Long idChain){
        PulseEntity recordEntity = (PulseEntity) manager
                .createQuery("from RecordEntity where chain = :chain and idChain = :idChain order by id desc")
                .setParameter("chain", chain.toString())
                .setParameter("idChain", idChain)
                .getSingleResult();

        return Optional.of(recordEntity);
    }

}
