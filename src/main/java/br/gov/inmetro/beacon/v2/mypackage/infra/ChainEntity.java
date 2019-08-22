package br.gov.inmetro.beacon.v2.mypackage.infra;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
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
