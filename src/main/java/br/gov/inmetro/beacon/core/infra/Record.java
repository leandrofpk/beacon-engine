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

@Entity
@Data
public class Record {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @Length(max = 20)
    private String versionBeacon;

    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm a")
    private LocalDateTime time;

//    private Long timeLong;

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

//    @Transient
//    @JsonIgnore
//    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm a")
//    private String timeData;

//    public LocalDateTime getTimeDate(){
//        return longToLocalDateTime(this.getTime().toString());
//    }

    private LocalDateTime longToLocalDateTime(String data){
        long millis = new Long(data);
//        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(new Long(data)), ZoneId.of("America/Sao_Paulo"));

//        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
        return localDateTime;
    }

//    @Transient
//    @JsonIgnore
//    private String dataUnixLike;
//

//    @Transient
//    private LocalDateTime timeLong(){
//
//    }

    public long getUnixTimestamp() {
        return time.atZone(ZoneId.of("America/Sao_Paulo")).toInstant().toEpochMilli();
    }

}
