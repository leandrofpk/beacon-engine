package br.gov.inmetro.beacon.v1.application.api;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class RecordSimpleDto implements Serializable {

    @NotNull @NotBlank
    private String rawData;

    @NotNull @NotBlank
    private String chain;

    @NotNull @NotBlank
    private String noiseSource;

    @NotNull @NotBlank
    private String frequency;

    @NotNull
    private String timeStamp;

    public RecordSimpleDto(String timeStamp, String rawData, String chain) {
        this.timeStamp = timeStamp;
        this.rawData = rawData;
        this.chain = chain;
    }


}
