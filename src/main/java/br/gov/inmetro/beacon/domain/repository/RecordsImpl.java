package br.gov.inmetro.beacon.domain.repository;

import br.gov.inmetro.beacon.application.api.RecordDto;
import br.gov.inmetro.beacon.infra.Record;
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
        Record r = (Record) manager.createQuery("from Record where chain = :chain order by id desc")
                .setParameter("chain", chain.toString())
                .setMaxResults(1)
                .getSingleResult();
        return new RecordDto(r);
    }

    @Transactional
    public Record last(Integer chain){
        try {
            return (Record) manager.createQuery("from Record where chain = :chain order by id desc")
                    .setParameter("chain", chain.toString())
                    .setMaxResults(1)
                    .getSingleResult();

        } catch(NoResultException ex){
            return null;
        }
    }

    @Transactional
    public RecordDto first(Integer chain){
        Record r = (Record) manager.createQuery("from Record where chain = :chain order by id")
                .setParameter("chain", chain.toString())
                .setMaxResults(1)
                .getSingleResult();

        return new RecordDto(r);

    }

    @Transactional(readOnly = true)
    public List<Record> obterTodos(Integer chain){
        return Collections.unmodifiableList(manager
                .createQuery("from Record where chain = :chain order by id desc")
                .setParameter("chain", chain.toString())
                .setMaxResults(20)
                .getResultList());
    }

    @Transactional(readOnly = true)
    public Long maxChain(Integer chain){

        Long singleResult = (Long) manager.createQuery(" select max(r.idChain) from Record r where r.chain = :chain ")
                .setParameter("chain", chain.toString())
                .getSingleResult();

        return singleResult == null ? 0 : singleResult;
    }

    @Transactional(readOnly = true)
    public Optional<Record> findByTimestamp(Integer chain, LocalDateTime timeStamp){

        Record record;

        try {

            record = (Record) manager.createQuery("from br.gov.inmetro.beacon.core.infra.Record r where r.chain = :chain and r.timeStamp = :timeStamp")
                    .setParameter("chain", chain.toString())
                    .setParameter("timeStamp", timeStamp).getSingleResult();
        } catch (NoResultException e){
            return Optional.empty();
        }

        return Optional.of(record);
    }

    @Override
    public Optional<Record> findByUnixTimeStamp(Integer chain, Long data) {
        Record record = (Record) manager.createQuery("from Record r where r.chain = :chain and r.unixTimeStamp = :unixTimeStamp")
                .setParameter("chain", chain.toString())
                .setParameter("unixTimeStamp", data).getSingleResult();
        return Optional.of(record);
    }

    @Transactional(readOnly = true)
    public Optional<Record> findByChainAndId(Integer chain, Long idChain){
        Record record = (Record) manager
                .createQuery("from Record where chain = :chain and idChain = :idChain order by id desc")
                .setParameter("chain", chain.toString())
                .setParameter("idChain", idChain)
                .getSingleResult();

        return Optional.of(record);
    }

}
