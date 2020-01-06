package br.gov.inmetro.beacon.engine.infra;

import br.gov.inmetro.beacon.engine.domain.pulse.Pulse;
import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "pulse", uniqueConstraints = @UniqueConstraint(columnNames = { "timeStamp", "chainIndex" }))
public class PulseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uri;

    private String version;

    private int cipherSuite;

    private int period;

    private String certificateId;

    private long chainIndex;

    private long pulseIndex;

    private ZonedDateTime timeStamp;

    private String localRandomValue;

    @Embedded
    private ExternalEntity externalEntity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pulseEntity", cascade = CascadeType.PERSIST)
    private List<ListValueEntity> listValueEntities = new ArrayList<>();

    private String precommitmentValue;

    private int statusCode;

    private String signatureValue;

    private String outputValue;

    private ZonedDateTime createdAt;

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
        this.externalEntity = new ExternalEntity(newPulse.getExternal().getSourceId(),
                                                 newPulse.getExternal().getStatusCode(),
                                                 newPulse.getExternal().getValue());
        this.precommitmentValue = newPulse.getPrecommitmentValue();
        this.statusCode = newPulse.getStatusCode();
        this.signatureValue = newPulse.getSignatureValue();
        this.outputValue = newPulse.getOutputValue();

        newPulse.getListValue().forEach(
                listValue -> this.listValueEntities.add(new ListValueEntity(
                                                            listValue.getValue(),
                                                            listValue.getType(),
                                                            listValue.getUri(),
                                                            this)));

    }

    @PrePersist
    public void prePersist() {
        createdAt = ZonedDateTime.now();
    }

}
