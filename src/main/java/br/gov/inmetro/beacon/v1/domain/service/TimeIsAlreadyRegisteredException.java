package br.gov.inmetro.beacon.v1.domain.service;


public class TimeIsAlreadyRegisteredException extends RuntimeException  {
    public TimeIsAlreadyRegisteredException(String message) {
        super(message);
    }
}
