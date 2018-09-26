package br.gov.inmetro.beacon.core.dominio.repositorio;

import br.gov.inmetro.beacon.api.RecordDto;
import br.gov.inmetro.beacon.core.infra.Record;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

public class RecordsImpl implements RecordsQueries {

    @PersistenceContext
    private EntityManager manager;

    public RecordDto lastDto(){
        Record r = (Record) manager.createQuery("from Record order by id desc").setMaxResults(1).getSingleResult();
        return new RecordDto(r);
    }

    public Record last(){
        try {
            return (Record) manager.createQuery("from Record order by id desc").setMaxResults(1).getSingleResult();
        } catch(NoResultException ex){
            return null;
        }
    }

    public RecordDto startChain(){
        Record r = (Record) manager.createQuery("from Record order by id").setMaxResults(1).getSingleResult();
        return new RecordDto(r);

    }

    public List<Record> obterTodos(){
        return manager
                .createQuery("from Record order by id desc")
                    .setMaxResults(20)
                    .getResultList();
    }



}
