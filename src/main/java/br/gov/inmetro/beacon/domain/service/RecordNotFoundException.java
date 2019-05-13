package br.gov.inmetro.beacon.domain.service;

public class RecordNotFoundException extends RuntimeException  {
    public RecordNotFoundException() {
        super();
    }
    public RecordNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public RecordNotFoundException(String message) {
        super(message);
    }
    public RecordNotFoundException(Throwable cause) {
        super(cause);
    }
}
