package br.gov.inmetro.beacon.core.dominio.repositorio;

import br.gov.inmetro.beacon.core.infra.PaginacaoUtil;
import br.gov.inmetro.beacon.core.infra.Registro;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class RegistrosImpl implements RegistrosQueries {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private PaginacaoUtil paginacaoUtil;

    public Registro ultimo(){
        return (Registro) manager.createQuery("from Registro order by id desc").setMaxResults(1).getSingleResult();
    }

    public Registro primeiro(){
        return (Registro) manager.createQuery("from Registro order by id").setMaxResults(1).getSingleResult();
    }

}
