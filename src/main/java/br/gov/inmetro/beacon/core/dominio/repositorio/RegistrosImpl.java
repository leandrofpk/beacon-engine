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

    @Autowired
    private PaginacaoUtil paginacaoUtil;

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public Page<Registro> filtrar(RegistroFilter filtro, Pageable pageable) {
        Criteria criteria = manager.unwrap(Session.class).createCriteria(Registro.class);

        paginacaoUtil.preparar(criteria, pageable);
        adicionarFiltro(filtro, criteria);

        return new PageImpl<>(criteria.list(), pageable, total(filtro));
    }

    private Long total(RegistroFilter filtro) {
        Criteria criteria = manager.unwrap(Session.class).createCriteria(Registro.class);
        adicionarFiltro(filtro, criteria);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    private void adicionarFiltro(RegistroFilter filtro, Criteria criteria) {
//        if (filtro != null) {
//            if (!StringUtils.isEmpty(filtro.getCountry())) {
//                criteria.add(Restrictions.ilike("country", filtro.getCountry(), MatchMode.ANYWHERE));
//            }
//        }
    }

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
