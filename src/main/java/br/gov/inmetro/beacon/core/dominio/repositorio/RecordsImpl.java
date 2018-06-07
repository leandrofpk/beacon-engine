package br.gov.inmetro.beacon.core.dominio.repositorio;

import br.gov.inmetro.beacon.core.infra.Record;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class RecordsImpl implements RecordsQueries {

    @PersistenceContext
    private EntityManager manager;

    public Record last(){
        return (Record) manager.createQuery("from Record order by id desc").setMaxResults(1).getSingleResult();
    }

    public Record startChain(){
        return (Record) manager.createQuery("from Record order by id").setMaxResults(1).getSingleResult();
    }

    public List<Record> obterTodos(){
        return manager
                .createQuery("from Record order by id desc")
                    .setMaxResults(20)
                    .getResultList();
    }

}
