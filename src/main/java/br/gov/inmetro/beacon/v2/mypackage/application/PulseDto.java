package br.gov.inmetro.beacon.v2.mypackage.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Data
@JacksonXmlRootElement(localName = "pulse")
public class PulseDto implements Serializable {

    private Long id;

//    private String chain;
//
//    private Long pulseIndex;

//    @NotNull @NotBlank
//    private String frequency;
//
//    @NotNull
//    private Long timeStamp;

//    @JsonIgnore
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm a")
    private LocalDateTime timeStampOriginal;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSz")
    private ZonedDateTime timestamp;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime timestamp2;


//    private Long unixTimeStamp;

//    private String seedValue;
//
//    private String previousOutputValue;
//
//    private String signatureValue;
//
//    private String outputValue;
//
//    private String statusCode;

//    @Lob
//    @JsonIgnore
//    private String rawData;

    public PulseDto() {
    }

//    public PulseDto(RecordEntity recordEntity){
//        this.id = recordEntity.getId();
//        this.pulseIndex = recordEntity.getIdChain();
//        this.frequency = recordEntity.getFrequency();
//        this.timeStamp = recordEntity.getTimeStampWork().atZone(ZoneId.of("America/Sao_Paulo")).toInstant().toEpochMilli();
//        this.timeStampOriginal = recordEntity.getTimeStampWork();
//        this.unixTimeStamp = recordEntity.getTimeStamp();
//        this.seedValue = recordEntity.getSeedValue();
//        this.previousOutputValue = recordEntity.getPreviousOutputValue();
//        this.signatureValue = recordEntity.getSignatureValue();
//        this.outputValue = recordEntity.getOutputValue();
//        this.statusCode = recordEntity.getStatusCode();
//        this.chain = recordEntity.getChain();
//    }
}
