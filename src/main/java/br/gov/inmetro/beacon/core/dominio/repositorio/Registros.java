package br.gov.inmetro.beacon.core.dominio.repositorio;

import br.gov.inmetro.beacon.core.infra.Registro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface Registros extends JpaRepository<Registro, Long>, RegistrosQueries {
    Registro findByHora(LocalDateTime dataTratada);
}
