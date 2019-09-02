package br.gov.inmetro.beacon.engine.domain.service;

public class BadRequestException extends RuntimeException  {
    public BadRequestException(String message) {
        super(message);
    }
}
