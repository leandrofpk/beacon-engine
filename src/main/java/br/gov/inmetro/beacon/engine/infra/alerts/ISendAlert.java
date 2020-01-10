package br.gov.inmetro.beacon.engine.infra.alerts;

public interface ISendAlert {
    void send(String message) throws SendAlertMailException;
}
