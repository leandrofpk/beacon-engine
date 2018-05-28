package br.gov.inmetro.beacon.core.dominio.repositorio;

import br.gov.inmetro.beacon.core.infra.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface Records extends JpaRepository<Record, Long>, RecordsQueries {
    Record findByTime(LocalDateTime dataTratada);
}
