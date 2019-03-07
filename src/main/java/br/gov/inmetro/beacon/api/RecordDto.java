package br.gov.inmetro.beacon.api;

import br.gov.inmetro.beacon.core.infra.Record;
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

    @NotNull
    private String version;

    @NotNull @NotBlank
    private String frequency;

    @NotNull
    private String timeStamp;

    @JsonIgnore
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm a")
    private LocalDateTime timeStampOriginal;

    private Long unixTimeStamp;

    @NotNull
    private String seedValue;

    @NotNull
    private String previousOutputValue;

    @NotNull
    private String signatureValue;

    @NotNull
    private String outputValue;

    @NotNull
    private String statusCode;

    @Lob
    @JsonIgnore
    private String rawData;

    public RecordDto() {
    }

    public RecordDto(Record record){
        this.id = record.getId();
        this.pulseIndex = record.getIdChain();
        this.version = record.getVersionBeacon();
        this.frequency = record.getFrequency();
        this.timeStamp = String.valueOf(record.getTimeStamp().atZone(ZoneId.of("America/Sao_Paulo")).toInstant().toEpochMilli());
        this.timeStampOriginal = record.getTimeStamp();
        this.unixTimeStamp = record.getUnixTimeStamp();
        this.seedValue = record.getSeedValue();
        this.previousOutputValue = record.getPreviousOutputValue();
        this.signatureValue = record.getSignatureValue();
        this.outputValue = record.getOutputValue();
        this.statusCode = record.getStatusCode();
        this.chain = record.getChain();
    }
}
