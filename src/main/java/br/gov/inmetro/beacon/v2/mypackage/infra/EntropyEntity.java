package br.gov.inmetro.beacon.v2.mypackage.infra;

import br.gov.inmetro.beacon.v2.mypackage.queue.EntropyDto;
import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
