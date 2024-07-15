package com.goatspec.application.gateways.email;

import com.goatspec.domain.entities.email.SendEmailParams;

public interface ISendEmailGateway {
    void send(SendEmailParams emailParams);
}
