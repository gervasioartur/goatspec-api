package com.goatspec.infrastructure.gateways.helpers.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Key;

@SpringBootTest
class SingKeyTests {
    @Autowired
    private SingKey singKey;

    @Test
    @DisplayName("Should create sing key with app secret from application")
    void shouldCreateSingKeyWithAppSecret() {
        Key token = singKey.getSignKey();
        Assertions.assertNotNull(token);
    }
}
