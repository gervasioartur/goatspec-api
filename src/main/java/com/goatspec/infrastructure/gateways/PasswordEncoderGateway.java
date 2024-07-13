package com.goatspec.infrastructure.gateways;

import com.goatspec.application.gateways.IPasswordEncoderGateway;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderGateway implements IPasswordEncoderGateway {
    private final PasswordEncoder passwordEncoder;

    public PasswordEncoderGateway(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(String password) {
        return this.passwordEncoder.encode(password);
    }
}
