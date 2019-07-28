package br.gov.inmetro.beacon.v1.application.api;

import lombok.Getter;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
public final class LocalRandomValueDto implements Serializable {

    private ZonedDateTime timeStamp;

    private String value;

    public LocalRandomValueDto(ZonedDateTime timeStamp, String value) {
        this.timeStamp = timeStamp;
        this.value = value;

        // TODO Aplicar o Hash aqui
//        CriptoUtilService.hashSha512Hexa(value);

    }


}
