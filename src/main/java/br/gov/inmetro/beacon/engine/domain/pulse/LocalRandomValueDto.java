package br.gov.inmetro.beacon.engine.domain.pulse;

import lombok.Getter;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

@Getter
public final class LocalRandomValueDto implements Serializable {

    private ZonedDateTime timeStamp;

    private String value;

    public LocalRandomValueDto(ZonedDateTime timeStamp, String value) {
        this.timeStamp = timeStamp;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalRandomValueDto that = (LocalRandomValueDto) o;
        return timeStamp.equals(that.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeStamp);
    }
}
