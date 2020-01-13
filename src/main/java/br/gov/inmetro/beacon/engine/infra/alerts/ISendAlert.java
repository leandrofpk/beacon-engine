package br.gov.inmetro.beacon.engine.infra.alerts;

import br.gov.inmetro.beacon.engine.domain.pulse.CombineDomainResult;

public interface ISendAlert {
    void sendError() throws SendAlertMailException;
    void sendWarning(CombineDomainResult combineDomainResult) throws SendAlertMailException;;
}
