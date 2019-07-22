package br.gov.inmetro.beacon.v1.application.api;

import br.gov.inmetro.beacon.v1.infra.PulseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@JacksonXmlRootElement(localName = "pulse")
public class PulseDto implements Serializable {

    private String uri;

    private String version;

    private int cipherSuite;

    private int period;

    private String certificateId;

    private long chainIndex;

    private long pulseIndex;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSz")
    private LocalDateTime timeStamp;

    private String localRandomValue;

    public PulseDto() {
    }

    public PulseDto(PulseEntity recordEntity){
        this.uri = recordEntity.getUri();
        this.version = recordEntity.getVersion();
        this.cipherSuite = recordEntity.getCipherSuite();
        this.period = recordEntity.getPeriod();
        this.certificateId = recordEntity.getCertificateId();
        this.chainIndex = recordEntity.getChainIndex();
        this.pulseIndex = recordEntity.getPulseIndex();
        this.timeStamp = recordEntity.getTimeStamp();
        this.localRandomValue = recordEntity.getLocalRandomValue();
    }
}
