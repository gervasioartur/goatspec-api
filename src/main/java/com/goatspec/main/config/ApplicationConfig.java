package com.goatspec.main.config;

import com.goatspec.infrastructure.gateways.helpers.security.SingKey;
import com.goatspec.infrastructure.gateways.helpers.token.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    @Bean
    public SingKey singKey() {
        return new SingKey();
    }

    @Bean
    public CreateToken createToken() {
        return new CreateToken(singKey());
    }

    @Bean
    public ExtractAllClaims extractAllClaims() {
        return new ExtractAllClaims(singKey());
    }

    @Bean
    public ExtractClaim extractClaim() {
        return new ExtractClaim(extractAllClaims());
    }

    @Bean
    public GenerateToken generateToken() {
        return new GenerateToken(createToken());
    }

    @Bean
    public GetUsernameFromToken getUsernameFromToken() {
        return new GetUsernameFromToken(extractClaim());
    }

    @Bean
    public IsTokenExpired isTokenExpired() {
        return new IsTokenExpired(extractClaim());
    }

    @Bean
    public IsValidToken isValidToken() {
        return new IsValidToken(getUsernameFromToken(), isTokenExpired());
    }

    @Bean
    public ValidateToken validateToken() {
        return new ValidateToken();
    }
}
