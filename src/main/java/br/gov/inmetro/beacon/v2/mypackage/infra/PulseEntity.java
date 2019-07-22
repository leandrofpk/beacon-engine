package br.gov.inmetro.beacon.v2.mypackage.infra;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "pulse", uniqueConstraints = @UniqueConstraint(columnNames = { "timeStamp", "chainIndex" }))
public class PulseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
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
}
