package br.gov.inmetro.beacon.v1.infra;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pulse", uniqueConstraints = @UniqueConstraint(columnNames = { "timeStamp", "chain" }))
@Data
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

    private LocalDateTime timeStamp;

    private String localRandomValue;

    public PulseEntity(){
    }

//    public String getRecordDataString(){
//        return String.format("%s%s%s%s%s%s",this.versionBeacon.trim(), this.frequency.trim(), this.timeStamp,
//                this.seedValue.trim(), this.previousOutputValue.trim(), this.statusCode.trim());
//    }

}
