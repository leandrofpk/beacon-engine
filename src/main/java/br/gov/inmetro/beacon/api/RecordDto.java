package br.gov.inmetro.beacon.api;

import br.gov.inmetro.beacon.core.infra.Record;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZoneId;

@Data
@JacksonXmlRootElement(localName = "record")
public class RecordDto implements Serializable {

    @NotNull
    private String version;

    @Transient
    private String frequency;

    @NotNull
    private String timeStamp;

//    @NotNull
//    private String timeStampLong;

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
        this.version = record.getVersionBeacon();
        this.frequency = "60";
//        this.timeStamp = String.valueOf(record.getTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        this.timeStamp = record.getTime().toString();
        this.seedValue = record.getSeedValue();
        this.previousOutputValue = record.getPreviousOutput();
        this.signatureValue = record.getSignature();
        this.outputValue = record.getSignature();
        this.statusCode = record.getStatus();
    }
}
