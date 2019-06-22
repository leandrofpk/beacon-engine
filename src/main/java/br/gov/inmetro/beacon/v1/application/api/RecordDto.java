package br.gov.inmetro.beacon.v1.application.api;

import br.gov.inmetro.beacon.v1.infra.RecordEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@JacksonXmlRootElement(localName = "record")
public class RecordDto implements Serializable {

    private Long id;

    private String chain;

    private Long pulseIndex;

    @NotNull @NotBlank
    private String frequency;

    @NotNull
    private Long timeStamp;

    @JsonIgnore
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm a")
    private LocalDateTime timeStampOriginal;

    private Long unixTimeStamp;

    private String seedValue;

    private String previousOutputValue;

    private String signatureValue;

    private String outputValue;

    private String statusCode;

    @Lob
    @JsonIgnore
    private String rawData;

    public RecordDto() {
    }

    public RecordDto(RecordEntity recordEntity){
        this.id = recordEntity.getId();
        this.pulseIndex = recordEntity.getIdChain();
        this.frequency = recordEntity.getFrequency();
        this.timeStamp = recordEntity.getTimeStampWork().atZone(ZoneId.of("America/Sao_Paulo")).toInstant().toEpochMilli();
        this.timeStampOriginal = recordEntity.getTimeStampWork();
        this.unixTimeStamp = recordEntity.getTimeStamp();
        this.seedValue = recordEntity.getSeedValue();
        this.previousOutputValue = recordEntity.getPreviousOutputValue();
        this.signatureValue = recordEntity.getSignatureValue();
        this.outputValue = recordEntity.getOutputValue();
        this.statusCode = recordEntity.getStatusCode();
        this.chain = recordEntity.getChain();
    }
}
