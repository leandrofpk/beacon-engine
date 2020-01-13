package br.gov.inmetro.beacon.engine.infra.alerts;

import br.gov.inmetro.beacon.engine.domain.pulse.CombineDomainResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static br.gov.inmetro.beacon.engine.infra.util.DateUtil.getCurrentTrucatedZonedDateTime;

@Component
public class SendAlertEmailImpl implements ISendAlert {

    private final JavaMailSender javaMailSender;

    private final Environment env;

    private ZonedDateTime dateTimeLastAlert;

    private static final Logger logger = LoggerFactory.getLogger(SendAlertEmailImpl.class);

    @Autowired
    public SendAlertEmailImpl(JavaMailSender javaMailSender, Environment env) {
        this.javaMailSender = javaMailSender;
        this.env = env;
        this.dateTimeLastAlert = null;
    }

    @Override
    public void sendError() throws SendAlertMailException {
        StringBuilder stringBuilder = new StringBuilder("ERROR: No number received");

        logger.error(stringBuilder.toString());

        if (Boolean.parseBoolean(env.getProperty("beacon.send.alerts.by.email"))) {
            if (sendAlertAgain()){
                String from = env.getProperty("beacon.mail.from");
                String to = env.getProperty("beacon.mail.to");

                sendSimpleMessage(from, to, null, "Inmetro Beacon - ERROR", stringBuilder.toString());
            }
        }
    }

    @Override
    public void sendWarning(CombineDomainResult combineDomainResult) throws SendAlertMailException {
        StringBuilder sb = new StringBuilder("WARNING: One or more sources were not received\n");

        combineDomainResult.getCombineErrorList().forEach( result -> sb.append("\n" + result) );

        if (Boolean.parseBoolean(env.getProperty("beacon.send.alerts.by.email"))) {
            if (sendAlertAgain()){
                String from = env.getProperty("beacon.mail.from");
                String to = env.getProperty("beacon.mail.to");
                sendSimpleMessage(from, to, null, "Inmetro Beacon - WARNING", sb.toString());
            }

        }
    }

    @Async
    protected void sendSimpleMessage(String from, String to, String co, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(from);
            message.setTo(to);

            if (!StringUtils.isEmpty(co)) {
                message.setCc(co);
            }

            message.setSubject(subject);

            message.setText(text + "\n\nSent from: " + getActiveProfiles() );

            javaMailSender.send(message);
        } catch (SendAlertMailException exception) {
            logger.error(exception.getMessage());
        }
    }

    private String getActiveProfiles(){
        String[] activeProfiles = env.getActiveProfiles();
        String out = null;
        for (String activeProfile : activeProfiles) {
            out = out + activeProfile;
        }
        return out;
    }

    private boolean sendAlertAgain(){
        if (dateTimeLastAlert == null){
            dateTimeLastAlert = getCurrentTrucatedZonedDateTime();
            return true;
        }

        long property = Long.parseLong(env.getProperty("beacon.mail.resend.alert-in-pulses"));

        long between = ChronoUnit.MINUTES.between(dateTimeLastAlert, getCurrentTrucatedZonedDateTime());
        if (between >= property){
            dateTimeLastAlert = getCurrentTrucatedZonedDateTime();
            return true;
        } else {
            return false;
        }
    }

}
