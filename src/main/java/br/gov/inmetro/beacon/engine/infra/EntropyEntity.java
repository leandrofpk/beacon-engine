package br.gov.inmetro.beacon.engine.infra;

import br.gov.inmetro.beacon.engine.queue.EntropyDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Entity
@Data
@Table(name = "entropy")
@NoArgsConstructor
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
