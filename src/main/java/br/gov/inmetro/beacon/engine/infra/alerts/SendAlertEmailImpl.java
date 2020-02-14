package br.gov.inmetro.beacon.engine.infra.alerts;

import br.gov.inmetro.beacon.engine.domain.pulse.CombineDomainResult;
import br.gov.inmetro.beacon.engine.domain.pulse.Pulse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
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
    public void sendException(Exception exception) throws SendAlertMailException {
        String subject = "Inmetro Beacon - EXCEPTION (BUG in pulse generation)";
        StringBuilder body = new StringBuilder();
        body.append(exception.getMessage());
        body.append(exception.getCause().getCause().toString());
        send(subject, body, true);
    }

    @Override
    public void sendTimestampAlreadyPublishedException(Pulse pulse) throws SendAlertMailException {
        String subject = "Inmetro Beacon - EXCEPTION (Timestamp Already Published)";
        StringBuilder body = new StringBuilder();
        body.append(String.format("Timestamp: %s\n\n", LocalDateTime.now()));
        body.append("The following pulse was discarded:\n\n");
        body.append(pulse.toString());
        logger.error(subject + ": timestamp:" + pulse.getTimeStamp());
        send(subject, body, true);
    }

    @Override
    public void sendError() throws SendAlertMailException {
        StringBuilder stringBuilder = new StringBuilder("ERROR: No numbers received");
        logger.error(stringBuilder.toString());
        String subject = "Inmetro Beacon - ERROR (No numbers received)";
        send(subject, new StringBuilder(), true);
    }

    @Override
    public void sendWarning(CombineDomainResult combineDomainResult) throws SendAlertMailException {
        String subject = "Inmetro Beacon - WARNING";
        StringBuilder body = new StringBuilder("WARNING: One or more sources were not received\n");
        combineDomainResult.getDomainResultInText().forEach( result -> body.append("\n" + result) );
        send(subject, body, false);
    }

    public void send(String subject, StringBuilder body, boolean sendImmediately){
        if (Boolean.parseBoolean(env.getProperty("beacon.send.alerts.by.email"))) {

            if (sendImmediately){
                sendList(subject, body.toString());
            } else {
                if (sendAlertAgain()){
                    sendList(subject, body.toString());
                }
            }
        }
    }

    private void sendList(String subject, String body){
        String from = env.getProperty("beacon.mail.from");
        String[] to = env.getProperty("beacon.mail.to").split(",");

        for (String email: to) {
            sendSimpleMessage(from, email, null, subject, body);
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

            message.setText(text + "\n\nSent from: " + env.getProperty("beacon.url") + "\n\n");

            javaMailSender.send(message);
        } catch (SendAlertMailException exception) {
            logger.error(exception.getMessage());
        }
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
