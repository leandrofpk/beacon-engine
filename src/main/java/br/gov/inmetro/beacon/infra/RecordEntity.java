package br.gov.inmetro.beacon.infra;

import br.gov.inmetro.beacon.domain.OriginEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.primitives.Longs;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.lang3.ArrayUtils.addAll;
import static org.apache.commons.lang3.ArrayUtils.nullToEmpty;

@Entity
@Table(name = "record")
@Data
public class RecordEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @Length(max = 20)
    private String versionBeacon;

    @NotNull
    private String frequency;

    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm a")
    private LocalDateTime timeStampWork;

    private Long timeStamp;

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

    @Lob
    private String rawData;

    private String chain;

    @NotNull
    private Long idChain;

    public RecordEntity(){
    }

    public String getRecordDataString(){
        return String.format("%s%s%s%s%s%s",this.versionBeacon.trim(), this.frequency.trim(), this.timeStamp,
                this.seedValue.trim(), this.previousOutputValue.trim(), this.statusCode.trim());
    }

    public byte[] getRecordDataBytes(){
//        return String.format("%s%s%s%s%s%s",this.versionBeacon.trim(),
//                this.frequency.trim(), this.timeStamp,
//                this.seedValue.trim(), this.previousOutputValue.trim(),
//                this.statusCode.trim());

        byte[] bytes1 = addAll(frequency.getBytes(UTF_8), Longs.toByteArray(timeStamp));
        byte[] bytes2 = addAll(seedValue.getBytes(UTF_8), previousOutputValue.getBytes(UTF_8));
        byte[] bytes3 = addAll(bytes1, bytes2);

        return bytes3;
    }

//    public byte getRecordDataBytes(){
//        byte[] bytes = this.frequency.trim().getBytes(UTF_8);
//
//        byte[] bytes1 = ArrayUtils.addAll(frequency.getBytes(), timeStamp.toString().getBytes());
//
//        byte[] bytes2 = ArrayUtils.addAll(frequency.getBytes(), timeStamp.toString().getBytes());
//
//
//        ArrayUtils.addAll(frequency.trim().getBytes(UTF_8), this.timeStamp.byteValue(),
//                this.seedValue.trim().getBytes(UTF_8), this.previousOutputValue.trim().getBytes(UTF_8),
//                this.statusCode.trim().getBytes(UTF_8));
//    }

}
