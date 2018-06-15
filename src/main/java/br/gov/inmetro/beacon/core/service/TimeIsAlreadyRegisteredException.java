package br.gov.inmetro.beacon.core.service;


public class TimeIsAlreadyRegisteredException extends RuntimeException  {
    public TimeIsAlreadyRegisteredException() {
        super();
    }
    public TimeIsAlreadyRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }
    public TimeIsAlreadyRegisteredException(String message) {
        super(message);
    }
    public TimeIsAlreadyRegisteredException(Throwable cause) {
        super(cause);
    }
}
