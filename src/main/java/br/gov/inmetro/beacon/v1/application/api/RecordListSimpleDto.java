package br.gov.inmetro.beacon.v1.application.api;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@JacksonXmlRootElement(localName = "record")
public class RecordListSimpleDto implements Serializable {


    @NotNull @NotBlank
    private String chain;

    @NotNull @NotBlank
    private String noiseSource;

    @NotNull @NotBlank
    private String frequency;

    @NotNull
    private String timeStamp;

    @NotEmpty
    private List<String> listRawData;
}
