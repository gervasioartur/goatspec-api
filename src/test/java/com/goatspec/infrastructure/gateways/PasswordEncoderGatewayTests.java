package com.goatspec.infrastructure.gateways;

import com.goatspec.application.gateways.IPasswordEncoderGateway;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@SpringBootTest
class PasswordEncoderGatewayTests {
    private IPasswordEncoderGateway passwordEncoderGateway;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        this.passwordEncoderGateway = new PasswordEncoderGateway(passwordEncoder);
    }

    @Test
    @DisplayName("Should encode the password")
    void shouldEncodePassword() {
        String password = "any_password";
        String encodedPassword = UUID.randomUUID().toString();
        Mockito.when(this.passwordEncoder.encode(password)).thenReturn(encodedPassword);
        String encodedPasswordResult =  this.passwordEncoderGateway.encode(password);
        Assertions.assertThat(encodedPasswordResult).isEqualTo(encodedPassword);
        Mockito.verify(this.passwordEncoder).encode(password);
    }
}
