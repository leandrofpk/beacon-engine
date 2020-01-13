package br.gov.inmetro.beacon.engine.infra.alerts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class SendAlertMailService {

    private final Environment env;

    private final SendAlertEmailImpl sendAlertEmail;

    @Autowired
    public SendAlertMailService(Environment env, SendAlertEmailImpl sendAlertEmail) {
        this.env = env;
        this.sendAlertEmail = sendAlertEmail;
    }

    public void sendError(){
        sendAlertEmail.sendError();
    }

    public void sendWarning(){

    }


}
