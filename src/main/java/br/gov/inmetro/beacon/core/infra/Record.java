package br.gov.inmetro.beacon.core.infra;

import br.gov.inmetro.beacon.core.dominio.OriginEnum;
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
//    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
//    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
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
    @NotNull //@Length(max = 20)
    private OriginEnum origin;

    @Transient
    private String dataUnixLike;

    public long getDataUnixLike() {
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

//    public String getNumeroTruncado() {
//        return numero.substring(0,20) + "...";
//    }
}
