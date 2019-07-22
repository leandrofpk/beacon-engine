package br.gov.inmetro.beacon.queue;

import lombok.Getter;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

@Getter
public class EntropyDto implements Serializable {

    private Long id;

    private String rawData;

    private int period;

    private byte noiseSource;

    private ZonedDateTime timeStamp;

    public EntropyDto() {
    }

    public EntropyDto(ZonedDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    // TODO Não funciona.  Ver o motivo
    public EntropyDto(ZonedDateTime timeStamp,
                      String rawData,
                      int period,
                      byte noiseSource) {
        this.timeStamp = timeStamp;
        this.rawData = rawData;
        this.period = period;
        this.noiseSource = noiseSource;
    }

    @Override
    public String toString() {
        return "EntropyDto{" +
                "id=" + id +
                ", rawData='" + rawData + '\'' +
                ", period=" + period +
                ", noiseSource=" + noiseSource +
                ", timeStamp=" + timeStamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntropyDto that = (EntropyDto) o;
        return getTimeStamp().equals(that.getTimeStamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTimeStamp());
    }
}
