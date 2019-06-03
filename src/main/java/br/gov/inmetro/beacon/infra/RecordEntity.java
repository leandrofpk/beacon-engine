package br.gov.inmetro.beacon.infra;

import br.gov.inmetro.beacon.domain.OriginEnum;
import br.gov.inmetro.beacon.domain.service.RecordNew;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.primitives.Longs;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.lang3.ArrayUtils.addAll;

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

    public RecordEntity(RecordNew recordNew, String chain, Long idChain){
        this.versionBeacon = recordNew.getVersion();
        this.frequency = String.valueOf(recordNew.getFrequency());
        this.timeStamp = recordNew.getTimeStamp();
        this.seedValue = recordNew.getSeed();
        this.previousOutputValue = recordNew.getPreviousOutput();
        this.signatureValue = recordNew.getSignature();
        this.chain = chain;
        this.idChain = idChain;
        this.origin = OriginEnum.BEACON;
    }


    public String getRecordDataString(){
        return String.format("%s%s%s%s%s%s",this.versionBeacon.trim(), this.frequency.trim(), this.timeStamp,
                this.seedValue.trim(), this.previousOutputValue.trim(), this.statusCode.trim());
    }

    public byte[] getRecordDataBytes(){

        byte[] bytes0 = addAll(versionBeacon.getBytes(UTF_8), frequency.getBytes(UTF_8));
        byte[] bytes1 = addAll(Longs.toByteArray(timeStamp), seedValue.getBytes(UTF_8));
        byte[] bytes2 = addAll(bytes0, bytes1);
        byte[] bytes3 = addAll(previousOutputValue.getBytes(UTF_8), statusCode.trim().getBytes(UTF_8));
        byte[] bytes4 = addAll(bytes2, bytes3);

        return bytes4;
    }

}
