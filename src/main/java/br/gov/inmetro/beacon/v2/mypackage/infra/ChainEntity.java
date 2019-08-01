package br.gov.inmetro.beacon.v2.mypackage.infra;

import lombok.Data;

import javax.persistence.*;

@Data
//@Entity
@Table(name = "chain")
public class ChainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String version;

    private int cipherSuite;

    private String cipherSuiteDescription;

    private int period;

    private long chainIndex;





}
