package br.gov.inmetro.beacon.api;

import br.gov.inmetro.beacon.core.infra.Record;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZoneId;

@Data
@JacksonXmlRootElement(localName = "record")
public class RecordDto implements Serializable {

    private String pulseIndex;

    @NotNull
    private String version;

    @NotNull @NotBlank
    private String frequency;

    @NotNull
    private String timeStamp;


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

    public RecordDto() {
    }

    public RecordDto(Record record){
        this.pulseIndex = record.getId().toString();
        this.version = record.getVersionBeacon();
        this.frequency = record.getFrequency();
        this.timeStamp = String.valueOf(record.getTimeStamp().atZone(ZoneId.of("America/Sao_Paulo")).toInstant().toEpochMilli());
        this.seedValue = record.getSeedValue();
        this.previousOutputValue = record.getPreviousOutputValue();
        this.signatureValue = record.getSignatureValue();
        this.outputValue = record.getOutputValue();
        this.statusCode = record.getStatusCode();
    }
}
