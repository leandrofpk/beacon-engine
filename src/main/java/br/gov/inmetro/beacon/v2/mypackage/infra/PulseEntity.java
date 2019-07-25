package br.gov.inmetro.beacon.v2.mypackage.infra;

import br.gov.inmetro.beacon.v2.mypackage.domain.pulse.Pulse;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "pulse", uniqueConstraints = @UniqueConstraint(columnNames = { "timeStamp", "chainIndex" }))
public class PulseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected String uri;

    protected String version;

    protected int cipherSuite;

    protected int period;

    protected String certificateId;

    protected long chainIndex;

    protected long pulseIndex;

    private java.time.ZonedDateTime timeStamp;

    protected String localRandomValue;

//    protected PulseType.External external;
//
//    protected List<PulseType.ListValue> listValue;

    protected String precommitmentValue;

    protected int statusCode;

    protected String signatureValue;

    protected String outputValue;

    public PulseEntity(){
    }

    public PulseEntity(Pulse newPulse){
        this.uri = newPulse.getUri();
        this.version = newPulse.getVersion();
        this.cipherSuite = newPulse.getCipherSuite();
        this.period = newPulse.getPeriod();
        this.certificateId = newPulse.getCertificateId();
        this.chainIndex = newPulse.getChainIndex();
        this.pulseIndex = newPulse.getPulseIndex();
        this.timeStamp = newPulse.getTimeStamp();
        this.localRandomValue = newPulse.getLocalRandomValue();
        this.precommitmentValue = newPulse.getPrecommitmentValue();
    }

}
