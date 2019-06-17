package br.gov.inmetro.beacon.queue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Getter
public class EntropyDto implements Serializable {

    private Long id;

    private String rawData;

    private String chain;

    private String frequency;

    private String noiseSource;

    @JsonIgnore
    private LocalDateTime timeStampDateTime;

    private Long timeStamp;

    public EntropyDto() {
    }

    public EntropyDto(Long timeStamp, String chain) {
        this.timeStamp = timeStamp;
        this.chain = chain;
    }

    // TODO Não funciona.  Ver o motivo
    public EntropyDto(LocalDateTime timeStamp,
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
        return "EntropyDto{" +
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
        EntropyDto noiseDto = (EntropyDto) o;
        return Objects.equals(getChain(), noiseDto.getChain()) &&
                Objects.equals(getTimeStamp(), noiseDto.getTimeStamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getChain(), getTimeStamp());
    }
}
