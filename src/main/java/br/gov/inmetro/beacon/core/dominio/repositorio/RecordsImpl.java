package br.gov.inmetro.beacon.core.dominio.repositorio;

import br.gov.inmetro.beacon.api.RecordDto;
import br.gov.inmetro.beacon.core.infra.Record;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RecordsImpl implements RecordsQueries {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    public RecordDto lastDto(){
        Record r = (Record) manager.createQuery("from Record order by id desc").setMaxResults(1).getSingleResult();
        return new RecordDto(r);
    }

    @Transactional
    public Record last(){
        try {
            return (Record) manager.createQuery("from Record order by id desc").setMaxResults(1).getSingleResult();
        } catch(NoResultException ex){
            return null;
        }
    }

    @Transactional
    public RecordDto first(){
        Record r = (Record) manager.createQuery("from Record order by id").setMaxResults(1).getSingleResult();
        return new RecordDto(r);

    }

    @Transactional
    public List<Record> obterTodos(){
        return manager
                .createQuery("from Record order by id desc")
                    .setMaxResults(20)
                    .getResultList();
    }



}
