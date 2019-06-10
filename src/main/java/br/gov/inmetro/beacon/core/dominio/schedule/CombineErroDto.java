package br.gov.inmetro.beacon.core.dominio.schedule;

import lombok.Getter;

@Getter
public class CombineErroDto {

    private final Long timestamp;

    private final int qtdSourcesExpected;

    private final String fontesUtilizadas;

    public CombineErroDto(long timestamp, int qtdSourcesExpected, String fonteUtilizada) {
        this.timestamp = timestamp;
        this.qtdSourcesExpected = qtdSourcesExpected;
        this.fontesUtilizadas = fonteUtilizada;
    }
}
