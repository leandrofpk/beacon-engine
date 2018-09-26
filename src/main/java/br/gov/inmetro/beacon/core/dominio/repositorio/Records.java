package br.gov.inmetro.beacon.core.dominio.repositorio;

import br.gov.inmetro.beacon.core.infra.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface Records extends JpaRepository<Record, Long>, RecordsQueries {
    Optional<Record> findByTimeStamp(LocalDateTime dataTratada);
//    Optional<Record> findByUnixTimeStamp(Long longTratado);
}
