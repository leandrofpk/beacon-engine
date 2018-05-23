package br.gov.inmetro.beacon.core.infra;

import br.gov.inmetro.beacon.core.dominio.OrigemEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Data
public class Registro {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @NotNull(message = "Número não pode ser nulo")
    private String numero;

    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime hora;

    @Lob
    @NotNull (message = "Assinatura não pode ser nula")
    private String assinatura;

    @Enumerated(EnumType.STRING)
    private OrigemEnum origem;

    @Transient
    private String dataUnixLike;

    public long getDataUnixLike() {
        return hora.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
