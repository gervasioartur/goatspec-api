package com.goatspec.main.config;

import com.goatspec.application.gateways.encrypt.IPasswordEncoderGateway;
import com.goatspec.infrastructure.gateways.authetication.AuthenticationGateway;
import com.goatspec.infrastructure.gateways.encrypt.PasswordEncoderGateway;
import com.goatspec.infrastructure.gateways.helpers.security.SingKey;
import com.goatspec.infrastructure.gateways.helpers.token.*;
import com.goatspec.infrastructure.gateways.user.LoadUserByUsernameGateway;
import com.goatspec.infrastructure.persisntence.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public IPasswordEncoderGateway passwordEncoderGateway(PasswordEncoder passwordEncoder) {
        return new PasswordEncoderGateway(passwordEncoder);
    }

    @Bean
    public UserDetailsService userDetailsService(IUserRepository userRepository) {
        return new LoadUserByUsernameGateway(userRepository);
    }

    @Bean
    public AuthenticationGateway authenticationGateway(AuthenticationManager authenticationManager, GenerateToken generateToken) {
        return new AuthenticationGateway(authenticationManager, generateToken);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider(IUserRepository userRepository) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(this.userDetailsService(userRepository));
        authenticationProvider.setPasswordEncoder(this.passwordEncoder());
        return authenticationProvider;
    }
}
