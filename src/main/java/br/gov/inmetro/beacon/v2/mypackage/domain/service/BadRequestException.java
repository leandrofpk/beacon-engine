package br.gov.inmetro.beacon.v2.mypackage.domain.service;

public class BadRequestException extends RuntimeException  {
    public BadRequestException(String message) {
        super(message);
    }
}
