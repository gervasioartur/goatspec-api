package com.goatspec.infrastructure.gateways.helpers.token;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;

@SpringBootTest
class IsTokenExpiredTests {
    @Autowired
    private IsTokenExpired isTokenExpired;
    @MockBean
    private ExtractClaim extractClaim;

    @Test
    @DisplayName("Should return true if the token is expired")
    void shouldReturnTrueIfTokenIsExpired() {
        String token = "any_token";
        Date date = new Date();
        Mockito.when(extractClaim.extract(Mockito.any(), Mockito.any())).thenReturn(date);
        isTokenExpired.isTokenExpired(token);
        Mockito.verify(extractClaim, Mockito.times(1)).extract(Mockito.any(), Mockito.any());
    }
}
