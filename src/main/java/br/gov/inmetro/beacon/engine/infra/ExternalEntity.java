package br.gov.inmetro.beacon.engine.infra;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Embeddable
public class ExternalEntity {

    @Column(name = "ext.srcId")
    private String sourceId;

    @Column(name = "ext.status")
    private short statusCode;

    @Column(name = "ext.value")
    private String value;

    public ExternalEntity() {
    }

    public ExternalEntity(String sourceId, short statusCode, String value) {
        this.sourceId = sourceId;
        this.statusCode = statusCode;
        this.value = value;
    }

}
