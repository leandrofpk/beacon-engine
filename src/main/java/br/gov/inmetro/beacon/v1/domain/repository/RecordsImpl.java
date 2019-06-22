package br.gov.inmetro.beacon.v1.domain.repository;

import br.gov.inmetro.beacon.v1.application.api.RecordDto;
import br.gov.inmetro.beacon.v1.infra.RecordEntity;
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
public class RecordsImpl implements RecordsQueries {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    public RecordDto lastDto(Integer chain){
        RecordEntity r = (RecordEntity) manager.createQuery("from RecordEntity where chain = :chain order by id desc")
                .setParameter("chain", chain.toString())
                .setMaxResults(1)
                .getSingleResult();
        return new RecordDto(r);
    }

    @Transactional
    public RecordEntity last(Integer chain){
        try {
            return (RecordEntity) manager.createQuery("from RecordEntity where chain = :chain order by id desc")
                    .setParameter("chain", chain.toString())
                    .setMaxResults(1)
                    .getSingleResult();

        } catch(NoResultException ex){
            return null;
        }
    }

    @Transactional
    public RecordDto first(Integer chain){
        RecordEntity r = (RecordEntity) manager.createQuery("from RecordEntity where chain = :chain order by id")
                .setParameter("chain", chain.toString())
                .setMaxResults(1)
                .getSingleResult();

        return new RecordDto(r);

    }

    @Transactional(readOnly = true)
    public List<RecordEntity> obterTodos(Integer chain){
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
    public Optional<RecordEntity> findByTimeStampWork(Integer chain, LocalDateTime timeStamp){

        RecordEntity recordEntity;

        try {

            recordEntity = (RecordEntity) manager.createQuery("from RecordEntity r " +
                    "where r.chain = :chain and r.timeStampWork = :timeStamp")
                    .setParameter("chain", chain.toString())
                    .setParameter("timeStamp", timeStamp).getSingleResult();
        } catch (NoResultException e){
            return Optional.empty();
        }

        return Optional.of(recordEntity);
    }

    @Override
    public Optional<RecordEntity> findByTimeStamp(Integer chain, Long data) {
        RecordEntity recordEntity = (RecordEntity) manager.createQuery("from RecordEntity r where r.chain = :chain and r.unixTimeStamp = :unixTimeStamp")
                .setParameter("chain", chain.toString())
                .setParameter("unixTimeStamp", data).getSingleResult();
        return Optional.of(recordEntity);
    }

    @Transactional(readOnly = true)
    public Optional<RecordEntity> findByChainAndId(Integer chain, Long idChain){
        RecordEntity recordEntity = (RecordEntity) manager
                .createQuery("from RecordEntity where chain = :chain and idChain = :idChain order by id desc")
                .setParameter("chain", chain.toString())
                .setParameter("idChain", idChain)
                .getSingleResult();

        return Optional.of(recordEntity);
    }

}
