package br.gov.inmetro.beacon.core.service;


public class TimeIsAlreadyRegisteredException extends RuntimeException  {
    public TimeIsAlreadyRegisteredException(String message) {
        super(message);
    }
}
