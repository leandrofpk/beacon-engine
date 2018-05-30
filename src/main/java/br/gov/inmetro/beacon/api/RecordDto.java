package br.gov.inmetro.beacon.api;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

public class RecordDto {

    @NotNull
    private LocalDateTime time;

    @NotNull
    @Length(max = 20)
    private String versionBeacon;

    @NotNull
    private String seedValue;

    @NotNull
    private String previousOutput;

    @Lob
    @NotNull
    private String signature;

    @Lob
    @NotNull
    private String outputValue;

    @Length(max = 20)
    @NotNull
    private String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecordDto recordDto = (RecordDto) o;
        return Objects.equals(time, recordDto.time);
    }

    @Override
    public int hashCode() {

        return Objects.hash(time);
    }
}
