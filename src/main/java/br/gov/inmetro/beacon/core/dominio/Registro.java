package br.gov.inmetro.beacon.core.dominio;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class Registro {

    private String numero;

    private LocalDateTime hora;

    private String assinatura;

    public Registro(String numero, String hora, String assinatura) {
        this.numero = numero;
        this.assinatura = assinatura;
        this.hora = converterData(hora);
    }

    public Registro(String numero,LocalDateTime hora, String assinatura) {
        this.numero = numero;
        this.assinatura = assinatura;
        this.hora = hora;
    }

    private LocalDateTime converterData(String hora) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy HH:mm");
        return LocalDateTime.parse(hora, formatter);
    }

}