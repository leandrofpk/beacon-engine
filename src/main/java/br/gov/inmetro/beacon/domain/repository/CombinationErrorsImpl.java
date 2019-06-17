package br.gov.inmetro.beacon.domain.repository;

import br.gov.inmetro.beacon.core.dominio.schedule.ProcessingErrorDto;
import br.gov.inmetro.beacon.infra.ProcessingErrorEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CombinationErrorsImpl implements CombinationErrorsQueries {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    public void persist(List<ProcessingErrorDto> list){
        list.forEach(dto -> manager.persist(new ProcessingErrorEntity(dto)));
    }

}
