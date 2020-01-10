package br.gov.inmetro.beacon.engine.infra.alerts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SendAlertEmailImpl implements ISendAlert {

    private final JavaMailSender javaMailSender;

    private final Environment env;

    private static final Logger logger = LoggerFactory.getLogger(SendAlertEmailImpl.class);

    @Autowired
    public SendAlertEmailImpl(JavaMailSender javaMailSender, Environment env) {
        this.javaMailSender = javaMailSender;
        this.env = env;
    }

    @Override
    public void send(String message) throws SendAlertMailException {
        StringBuilder stringBuilder = formatMessage(message);

        logger.error(stringBuilder.toString());

        if (Boolean.parseBoolean(env.getProperty("beacon.send.alerts.by.email"))) {
            String from = env.getProperty("beacon.mail.from");
            String to = env.getProperty("beacon.mail.to");

            sendSimpleMessage(from, to, null, "um assunto", stringBuilder.toString());
        }

    }

    private StringBuilder formatMessage(String message){
        StringBuilder sb = new StringBuilder();
        sb.append(message);
        sb.append("\n");
        sb.append("beacon.number-of-entropy-sources: " + env.getProperty("beacon.number-of-entropy-sources"));
        sb.append("\n");
        sb.append("Ennvironment:" + "[]");

        return sb;
    }

    @Async
    protected void sendSimpleMessage(String from, String to, String co, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

//            message.setFrom(env.getProperty("exportador.sender"));
            message.setFrom(from);
            message.setTo(to);

            if (!StringUtils.isEmpty(co)) {
                message.setCc(co);
            }

            message.setSubject(subject);
            message.setText(text + "\n\n Enviado de: " + env.getProperty("exportador.url"));

            javaMailSender.send(message);
        } catch (SendAlertMailException exception) {
            logger.error(exception.getMessage());
        }
    }
}
