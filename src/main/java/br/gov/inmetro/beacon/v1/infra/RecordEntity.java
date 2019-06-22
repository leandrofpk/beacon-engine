package br.gov.inmetro.beacon.v1.infra;

import br.gov.inmetro.beacon.v1.domain.OriginEnum;
import br.gov.inmetro.beacon.v1.domain.service.RecordNew;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
        this.statusCode = recordNew.getStatusCode();
        this.outputValue = recordNew.getOutput();
        this.chain = chain;
        this.idChain = idChain;
        this.origin = OriginEnum.BEACON;

        this.timeStampWork = DateUtil.longToLocalDateTime(String.valueOf(recordNew.getTimeStamp()));
    }


    public String getRecordDataString(){
        return String.format("%s%s%s%s%s%s",this.versionBeacon.trim(), this.frequency.trim(), this.timeStamp,
                this.seedValue.trim(), this.previousOutputValue.trim(), this.statusCode.trim());
    }

}
