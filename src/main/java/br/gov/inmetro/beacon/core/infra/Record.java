package br.gov.inmetro.beacon.core.infra;

import br.gov.inmetro.beacon.core.dominio.OriginEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Entity
@Data
public class Record {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @Length(max = 20)
    private String versionBeacon;

    @NotNull
    private String frequency;

    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm a")
    private LocalDateTime timeStamp;

    private Long unixTimeStamp;

    @Lob
    @NotNull
    private String seedValue;

    @Lob
    @NotNull
    private String previousOutputValue;

    @Lob
    @NotNull
    private String signatureValue;

    @Lob
    @NotNull
    private String outputValue;

    @Length(max = 20)
    @NotNull
    private String statusCode;

    @Enumerated(EnumType.STRING)
    @NotNull
    @JsonIgnore
    private OriginEnum origin;

    public Record(){
    }

}
