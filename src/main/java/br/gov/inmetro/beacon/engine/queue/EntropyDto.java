package br.gov.inmetro.beacon.engine.queue;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/*
    DTO used to be transmitted via rabbitmq
 */
@Getter
public final class EntropyDto implements Serializable {

    private final String rawData;

    private final int period;

    private final String noiseSource;

    private final ZonedDateTime timeStamp;

    public EntropyDto(@JsonProperty("rawData") String rawData,
                      @JsonProperty("period") int period,
                      @JsonProperty("noiseSource") String noiseSource,
                      @JsonProperty("timeStamp") String timeStamp) {
        this.timeStamp = ZonedDateTime.parse(timeStamp, DateTimeFormatter.ISO_DATE_TIME);
        this.rawData = rawData;
        this.period = period;
        this.noiseSource = noiseSource;
    }

    @Override
    public String toString() {
        return "EntropyDto{" +
                "rawData='" + rawData + '\'' +
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
