package br.gov.inmetro.beacon.v2.mypackage.infra;

import br.gov.inmetro.beacon.v2.mypackage.queue.EntropyDto;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Entity
@Data
@Table(name = "entropy")
public class EntropyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String rawData;

    @NotNull
    private int period;

    @NotNull
    private String noiseSource;

    @NotNull
    private ZonedDateTime timeStamp;

    public EntropyEntity(EntropyDto entropyDto) {
        this.rawData = entropyDto.getRawData();
        this.period = entropyDto.getPeriod();
        this.noiseSource = entropyDto.getNoiseSource();
        this.timeStamp = entropyDto.getTimeStamp();
    }
}
