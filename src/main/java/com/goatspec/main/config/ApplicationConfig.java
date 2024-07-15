package com.goatspec.main.config;

import com.goatspec.application.gateways.authentication.IAuthenticationGateway;
import com.goatspec.application.gateways.email.ISendEmailGateway;
import com.goatspec.application.gateways.encrypt.IPasswordEncoderGateway;
import com.goatspec.application.gateways.user.IUserGateway;
import com.goatspec.application.useCases.contracts.authentication.IGetLoggedUserInfoUseCase;
import com.goatspec.application.useCases.contracts.authentication.ISinginUseCase;
import com.goatspec.application.useCases.implementations.authentication.GetLoggedUserInfoUseCase;
import com.goatspec.application.useCases.implementations.authentication.SinginUseCase;
import com.goatspec.infrastructure.gateways.authetication.AuthenticationGateway;
import com.goatspec.infrastructure.gateways.email.SendEmailGateway;
import com.goatspec.infrastructure.gateways.encrypt.PasswordEncoderGateway;
import com.goatspec.infrastructure.gateways.helpers.security.SingKey;
import com.goatspec.infrastructure.gateways.helpers.token.*;
import com.goatspec.infrastructure.gateways.mappers.UserEntityMapper;
import com.goatspec.infrastructure.gateways.user.LoadUserByUsernameGateway;
import com.goatspec.infrastructure.persisntence.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    @Value("${app.email.sender}")
    private String appMailSender;

    @Value("${app.email.password}")
    private String password;

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
    public LoadUserByUsernameGateway loadUserByUsernameGateway(IUserRepository userRepository) {
        return new LoadUserByUsernameGateway(userRepository);
    }

    @Bean
    public UserDetailsService userDetailsService(IUserRepository userRepository) {
        return new LoadUserByUsernameGateway(userRepository);
    }

    @Bean
    public AuthenticationGateway authenticationGateway(AuthenticationManager authenticationManager, GenerateToken generateToken, IUserRepository userRepository, UserEntityMapper userEntityMapper) {
        return new AuthenticationGateway(authenticationManager, generateToken, userRepository, userEntityMapper);
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

    @Bean
    public ISinginUseCase singinUseCase(IUserGateway userGateway, IAuthenticationGateway authenticationGateway) {
        return new SinginUseCase(userGateway, authenticationGateway);
    }

    @Bean
    public IGetLoggedUserInfoUseCase getLoggedUserInfoUseCase(IAuthenticationGateway authenticationGateway) {
        return new GetLoggedUserInfoUseCase(authenticationGateway);
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername(appMailSender);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    @Bean
    public ISendEmailGateway sendEmailGateway(JavaMailSender mailSender){
        return new SendEmailGateway(mailSender);
    }

}
