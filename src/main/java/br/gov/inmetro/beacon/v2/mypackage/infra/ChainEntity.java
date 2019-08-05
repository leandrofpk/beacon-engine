package br.gov.inmetro.beacon.v2.mypackage.infra;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "chain")
public class ChainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String version;

    @NotNull
    private int cipherSuite;

    @NotNull
    private String cipherSuiteDescription;

    @NotNull
    private int period;

    @NotNull
    private long chainIndex;

    private boolean active;


}
