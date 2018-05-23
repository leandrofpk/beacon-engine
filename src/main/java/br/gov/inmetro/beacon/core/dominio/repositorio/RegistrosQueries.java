package br.gov.inmetro.beacon.core.dominio.repositorio;

import br.gov.inmetro.beacon.core.infra.Registro;
import br.gov.inmetro.beacon.core.infra.RegistroFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RegistrosQueries {
    Registro ultimo();
    Registro primeiro();
    Page<Registro> filtrar(RegistroFilter filtro, Pageable pageable);
    List<Registro> obterTodos();
}
