package br.gov.inmetro.beacon.application.api;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@JacksonXmlRootElement(localName = "record")
public class RecordSimpleDto implements Serializable {

    @NotNull @NotBlank
    private String rawData;

    @NotNull @NotBlank
    private String chain;

    @NotNull @NotBlank
    private String frequency;

    @NotNull
    private String timeStamp;

}
