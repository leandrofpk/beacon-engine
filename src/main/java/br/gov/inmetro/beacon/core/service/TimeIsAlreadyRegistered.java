package br.gov.inmetro.beacon.core.service;


public class TimeIsAlreadyRegistered extends RuntimeException  {
    public TimeIsAlreadyRegistered() {
        super();
    }
    public TimeIsAlreadyRegistered(String message, Throwable cause) {
        super(message, cause);
    }
    public TimeIsAlreadyRegistered(String message) {
        super(message);
    }
    public TimeIsAlreadyRegistered(Throwable cause) {
        super(cause);
    }
}
