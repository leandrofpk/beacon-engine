package br.gov.inmetro.beacon.v2.mypackage.infra;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "list_value")
public class ListValueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;

    private String type;

    private String uri;

    @ManyToOne
    @JoinColumn(name = "pulse_id")
    private PulseEntity pulseEntity;

    public ListValueEntity() {
    }

    public ListValueEntity(String value, String type, String uri) {
        this.value = value;
        this.type = type;
        this.uri = uri;
    }

}
