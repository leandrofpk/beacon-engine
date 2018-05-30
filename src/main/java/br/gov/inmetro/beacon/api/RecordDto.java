package br.gov.inmetro.beacon.api;

import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@XmlRootElement(name = "record")
@XmlAccessorType(XmlAccessType.FIELD)
public class RecordDto extends ResourceSupport implements Serializable {

    @NotNull
    private LocalDateTime time;

    @NotNull
    @Length(max = 20)
    private String versionBeacon;

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

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getVersionBeacon() {
        return versionBeacon;
    }

    public void setVersionBeacon(String versionBeacon) {
        this.versionBeacon = versionBeacon;
    }

    public String getSeedValue() {
        return seedValue;
    }

    public void setSeedValue(String seedValue) {
        this.seedValue = seedValue;
    }

    public String getPreviousOutput() {
        return previousOutput;
    }

    public void setPreviousOutput(String previousOutput) {
        this.previousOutput = previousOutput;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(String outputValue) {
        this.outputValue = outputValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RecordDto recordDto = (RecordDto) o;
        return Objects.equals(
                getTime(), recordDto.getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTime());
    }

    @Override
    public String toString() {
        return "RecordDto{" +
                "time=" + time +
                ", versionBeacon='" + versionBeacon + '\'' +
                ", seedValue='" + seedValue + '\'' +
                ", previousOutput='" + previousOutput + '\'' +
                ", signature='" + signature + '\'' +
                ", outputValue='" + outputValue + '\'' +
                ", status='" + status + '\'' +
                '}';
    }


}
