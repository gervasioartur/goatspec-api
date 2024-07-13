package com.goatspec.infrastructure.gateways.helpers.token;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
class GenerateTokenTests {
    @Autowired
    private GenerateToken generateToken;
    @MockBean
    private CreateToken createToken;

    @Test
    @DisplayName("Should return null if create token return null")
    void shouldReturnNullIfCreateTokenReturnNull() {
        Map<String, Object> claims = new HashMap<>();
        String username = "any_username";
        String token = null;

        Mockito.when(createToken.create(claims, username)).thenReturn(token);

        String result = this.generateToken.generate(username);

        Mockito.verify(createToken, Mockito.times(1)).create(claims, username);
        Assertions.assertThat(result).isNull();
    }

    @Test
    @DisplayName("Should return the generated token")
    void shouldReturnTheGeneratedToken() {
        Map<String, Object> claims = new HashMap<>();
        String username = "any_username";
        String token = UUID.randomUUID().toString();

        Mockito.when(createToken.create(claims, username)).thenReturn(token);

        String result = this.generateToken.generate(username);

        Mockito.verify(createToken, Mockito.times(1)).create(claims, username);
        Assertions.assertThat(result).isEqualTo(token);
    }

}
