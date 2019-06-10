package br.gov.inmetro.beacon.queue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Getter
public class NoiseDto implements Serializable {

    private String rawData;

    private String chain;

    private String frequency;

    private String noiseSource;

    @JsonIgnore
    private LocalDateTime timeStampDateTime;

    private Long timeStamp;

    public NoiseDto() {
    }

    public NoiseDto(Long timeStamp, String chain) {
        this.timeStamp = timeStamp;
        this.chain = chain;
    }

    // TODO Não funciona.  Ver op motivo
    public NoiseDto(LocalDateTime timeStamp,
                    String rawData,
                    String chain,
                    String frequency,
                    String noiseSource) {
        this.timeStampDateTime = timeStamp;
        this.rawData = rawData;
        this.chain = chain;
        this.frequency = frequency;
        this.timeStamp = this.timeStampDateTime.atZone(ZoneId.of("America/Sao_Paulo")).toInstant().toEpochMilli();
        this.noiseSource = noiseSource;
    }



    @Override
    public String toString() {
        return "NoiseDto{" +
                "rawData='" + rawData + '\'' +
                ", chain='" + chain + '\'' +
                ", frequency='" + frequency + '\'' +
                ", noiseSource='" + noiseSource + '\'' +
                ", timeStampDateTime=" + timeStampDateTime +
                ", timeStamp=" + timeStamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoiseDto noiseDto = (NoiseDto) o;
        return Objects.equals(getChain(), noiseDto.getChain()) &&
                Objects.equals(getTimeStamp(), noiseDto.getTimeStamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getChain(), getTimeStamp());
    }
}
