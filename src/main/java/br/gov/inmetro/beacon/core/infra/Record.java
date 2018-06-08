package br.gov.inmetro.beacon.core.infra;

import br.gov.inmetro.beacon.core.dominio.OriginEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Data
public class Record {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @Length(max = 20)
    private String versionBeacon;

    @NotNull
    private LocalDateTime time;

    @NotNull
    private String seedValue;

    @NotNull
    private String previousOutput;

    @Lob
    @NotNull
    private String signature;

    @Lob
    @NotNull
    private String outputValue;

    @Length(max = 20)
    @NotNull
    private String status;

    @Enumerated(EnumType.STRING)
    @NotNull
    @JsonIgnore
    private OriginEnum origin;

    @Transient
    @JsonIgnore
    private String dataUnixLike;

    public long getDataUnixLike() {
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

}
