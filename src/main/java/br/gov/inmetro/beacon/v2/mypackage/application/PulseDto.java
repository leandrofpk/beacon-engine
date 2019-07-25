package br.gov.inmetro.beacon.v2.mypackage.application;

import br.gov.inmetro.beacon.v2.mypackage.infra.PulseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.io.Serializable;

@Data
@JacksonXmlRootElement(localName = "pulse")
public class PulseDto implements Serializable {

    private Long id;

    protected String uri;

    protected String version;

    protected int cipherSuite;

    protected int period;

    protected String certificateId;

    protected long chainIndex;

    protected long pulseIndex;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSz")
    private java.time.ZonedDateTime timeStamp;

    protected String localRandomValue;

//    protected PulseType.External external;
//
//    protected List<PulseType.ListValue> listValue;

    protected String precommitmentValue;

    protected int statusCode;

    protected String signatureValue;

    protected String outputValue;

//    @JsonFormat(pattern = "dd/MM/yyyy hh:mm a")
//    private LocalDateTime timeStampOriginal;
//
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSz")
//    private ZonedDateTime timestamp;
//
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
//    private LocalDateTime timestamp2;

    public PulseDto() {
    }

    public PulseDto(PulseEntity pulseEntity){
        this.id = pulseEntity.getId();
        this.pulseIndex = pulseEntity.getPulseIndex();
        this.period = pulseEntity.getPeriod();
        this.timeStamp = pulseEntity.getTimeStamp();
        this.uri = pulseEntity.getUri();
        this.certificateId = pulseEntity.getCertificateId();
        this.chainIndex = pulseEntity.getChainIndex();
        this.localRandomValue = pulseEntity.getLocalRandomValue();
        this.precommitmentValue = pulseEntity.getPrecommitmentValue();
        this.signatureValue = pulseEntity.getSignatureValue();
        this.outputValue = pulseEntity.getOutputValue();
        this.statusCode = pulseEntity.getStatusCode();
    }
}
