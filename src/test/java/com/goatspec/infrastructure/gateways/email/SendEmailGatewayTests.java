package com.goatspec.infrastructure.gateways.email;

import com.goatspec.application.gateways.email.ISendEmailGateway;
import com.goatspec.domain.entities.email.SendEmailParams;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@SpringBootTest
class SendEmailGatewayTests {
    @Autowired
    private ISendEmailGateway sendEmailGateway;
    @MockBean
    private JavaMailSender javaMailSender;


    @Test
    @DisplayName("Should throw Mail exception if main sender throws")
    void shouldThrowMailExceptionIfSenderThrows() {
        SendEmailParams request = new SendEmailParams("recipient@example.com","Test Subject","Test Body");

        doThrow(RuntimeException.class).when(javaMailSender).send(Mockito.any(SimpleMailMessage.class));
        Throwable exception = Assertions.catchThrowable(() -> this.sendEmailGateway.send(request));
        Assertions.assertThat(exception).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Should send email")
    void shouldSendEmail() {
        SendEmailParams request = new SendEmailParams("recipient@example.com","Test Subject","Test Body");

        this.sendEmailGateway.send(request);

        SimpleMailMessage expectedMessage = new SimpleMailMessage();
        expectedMessage.setFrom("gervasioarthur@gmail.com");
        expectedMessage.setTo("recipient@example.com");
        expectedMessage.setSubject("Test Subject");
        expectedMessage.setText("Test Body");

        verify(javaMailSender).send(expectedMessage);
    }
}
