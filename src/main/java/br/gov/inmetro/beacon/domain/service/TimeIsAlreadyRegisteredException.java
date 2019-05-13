package br.gov.inmetro.beacon.domain.service;


public class TimeIsAlreadyRegisteredException extends RuntimeException  {
    public TimeIsAlreadyRegisteredException(String message) {
        super(message);
    }
}
