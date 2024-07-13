package com.goatspec.infrastructure.gateways.helpers.token;

import com.goatspec.infrastructure.gateways.helpers.security.SingKey;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class CreateTokenTests {
    @Autowired
    private CreateToken createToken;

    @MockBean
    private SingKey singKey;

    @Test
    @DisplayName("Should create token")
    void shouldCreateToken() {
        String appSecret = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
        Key mockKey = new SecretKeySpec(appSecret.getBytes(), "HmacSHA256");
        Map<String, Object> claims = new HashMap<>();
        String username = "any_username";
        Mockito.when(singKey.getSignKey()).thenReturn(mockKey);
        String token = createToken.create(claims, username);
        Assertions.assertThat(token).isNotNull();
    }

    @Test
    @DisplayName("Should not create token if SingKey returns null")
    void shouldNotCreateTokenIfSingKeyReturnsNull() {
        Map<String, Object> claims = new HashMap<>();
        String username = "any_username";
        Mockito.when(singKey.getSignKey()).thenReturn(null);
        Throwable exception = Assertions.catchThrowable(() -> createToken.create(claims, username));
        Assertions.assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }
}
