package br.gov.inmetro.beacon.core.dominio.repositorio;

import br.gov.inmetro.beacon.core.infra.PaginacaoUtil;
import br.gov.inmetro.beacon.core.infra.Registro;
import br.gov.inmetro.beacon.core.infra.RegistroFilter;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class RegistrosImpl implements RegistrosQueries {

    @PersistenceContext
    private EntityManager manager;

    public Registro ultimo(){
        return (Registro) manager.createQuery("from Registro order by id desc").setMaxResults(1).getSingleResult();
    }

    public Registro primeiro(){
        return (Registro) manager.createQuery("from Registro order by id").setMaxResults(1).getSingleResult();
    }

    public List<Registro> obterTodos(){
        return manager
                .createQuery("from Registro order by id desc")
                    .setMaxResults(20)
                    .getResultList();
    }

}
