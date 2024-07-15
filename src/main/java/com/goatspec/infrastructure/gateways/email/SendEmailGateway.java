package com.goatspec.infrastructure.gateways.email;

import com.goatspec.application.gateways.email.ISendEmailGateway;
import com.goatspec.domain.entities.email.SendEmailParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


public class SendEmailGateway implements ISendEmailGateway {
    private final JavaMailSender mailSender;

    @Value("${app.email.sender}")
    private String appMailSender;

    public SendEmailGateway(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(SendEmailParams emailParams) {
        SimpleMailMessage message = new SimpleMailMessage();
        String fromEmail = this.appMailSender;
        message.setFrom(fromEmail);
        message.setTo(emailParams.receiver());
        message.setSubject(emailParams.subject());
        message.setText((String) emailParams.body());
        mailSender.send(message);
    }
}
