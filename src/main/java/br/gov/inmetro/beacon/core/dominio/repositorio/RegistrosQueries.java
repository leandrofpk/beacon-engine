package br.gov.inmetro.beacon.core.dominio.repositorio;

import br.gov.inmetro.beacon.core.infra.Registro;

public interface RegistrosQueries {
    Registro ultimo();
    Registro primeiro();
}
