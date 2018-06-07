package br.gov.inmetro.beacon.api;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class RecordDto implements Serializable {

    @NotNull
    private String time;

    @NotNull
    private String versionBeacon;

    @NotNull
    private String seedValue;

    @NotNull
    private String previousOutput;

    @NotNull
    private String signature;

    @NotNull
    private String outputValue;

    @Length(max = 20)
    @NotNull
    private String status;

}
