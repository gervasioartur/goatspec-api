package com.goatspec.infrastructure.gateways;

import com.goatspec.application.gateways.IAuthenticationGateway;
import com.goatspec.infrastructure.gateways.helpers.token.GenerateToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class AuthenticationGateway implements IAuthenticationGateway {
    private final AuthenticationManager authenticationManager;
    private final GenerateToken generateToken;

    public AuthenticationGateway(AuthenticationManager authenticationManager, GenerateToken generateToken) {
        this.authenticationManager = authenticationManager;
        this.generateToken = generateToken;
    }

    @Override
    public String authenticate(String username, String password) {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return this.generateToken.generate(username);
    }
}
