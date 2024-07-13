package com.goatspec.infrastructure.gateways.helpers.token;

import io.jsonwebtoken.Claims;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class GetUsernameFromTokenTests {
    @MockBean
    private ExtractClaim extractClaim;

    @Test
    @DisplayName("Should return username")
    void ShouldReturnNullIfCantGetTheSubject() {
        String username = null;
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnZXJ2YXNpbzk5IiwiaWF0IjoxNzE0MDY0ODY0LCJleHAiOjE3MTQwNjY2NjR9.rrW61NJaWasxsqCn4a-pME1Xn_KDyoPQfNO3QJ5P1go";
        Mockito.when(extractClaim.extract(token, Claims::getSubject)).thenReturn(username);
        GetUsernameFromToken getUsernameFromToken = new GetUsernameFromToken(extractClaim);
        String result = getUsernameFromToken.get(token);
        Assertions.assertThat(result).isEqualTo(username);
    }

}
