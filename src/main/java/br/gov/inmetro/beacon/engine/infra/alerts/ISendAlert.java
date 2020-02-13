package br.gov.inmetro.beacon.engine.infra.alerts;

import br.gov.inmetro.beacon.engine.domain.pulse.CombineDomainResult;
import br.gov.inmetro.beacon.engine.domain.pulse.Pulse;

public interface ISendAlert {
    void sendException(Exception exception) throws SendAlertMailException;
    void sendTimestampAlreadyPublishedException(Pulse pulse) throws SendAlertMailException;
    void sendError() throws SendAlertMailException;
    void sendWarning(CombineDomainResult combineDomainResult) throws SendAlertMailException;;
}
