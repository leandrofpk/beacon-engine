package br.gov.inmetro.beacon.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Getter
public class RegistroDto {

    private String numero;

    private LocalDateTime hora;

    private String assinatura;

    public RegistroDto(String numero, String hora, String assinatura) {
        this.numero = numero;
        this.assinatura = assinatura;
        this.hora = converterData(hora);
    }

    public RegistroDto(String numero, LocalDateTime hora, String assinatura) {
        this.numero = numero;
        this.assinatura = assinatura;
        this.hora = hora;
    }

    private LocalDateTime converterData(String hora) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy HH:mm");
        return LocalDateTime.parse(hora, formatter);
    }

    public long getDataUnixLike() {
        return hora.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }


    @Override
    public String toString() {
        return "RegistroDto{" +
                "hora=" + hora +
                ", hora=" + getDataUnixLike() +
                ", numero='" + numero + '\'' +
                ", assinatura='" + assinatura + '\'' +
                '}';
    }
}
