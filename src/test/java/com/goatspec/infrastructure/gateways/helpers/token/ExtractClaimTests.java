package com.goatspec.infrastructure.gateways.helpers.token;

import io.jsonwebtoken.Claims;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

@SpringBootTest
class ExtractClaimTests {
    @Autowired
    private ExtractClaim extractClaim;
    @MockBean
    private ExtractAllClaims extractAllClaims;

    @Test
    @DisplayName("Shoudl return the claim")
    void shouldReturnTheClaim() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnZXJ2YXNpbzk5IiwiaWF0IjoxNzE0MDY0ODY0LCJleHAiOjE3MTQwNjY2NjR9.rrW61NJaWasxsqCn4a-pME1Xn_KDyoPQfNO3QJ5P1go";
        Claims claims = new Claims() {
            @Override
            public String getIssuer() {
                return "";
            }

            @Override
            public Claims setIssuer(String iss) {
                return null;
            }

            @Override
            public String getSubject() {
                return "gervasio99";
            }

            @Override
            public Claims setSubject(String sub) {
                return null;
            }

            @Override
            public String getAudience() {
                return "";
            }

            @Override
            public Claims setAudience(String aud) {
                return null;
            }

            @Override
            public Date getExpiration() {
                return null;
            }

            @Override
            public Claims setExpiration(Date exp) {
                return null;
            }

            @Override
            public Date getNotBefore() {
                return null;
            }

            @Override
            public Claims setNotBefore(Date nbf) {
                return null;
            }

            @Override
            public Date getIssuedAt() {
                return null;
            }

            @Override
            public Claims setIssuedAt(Date iat) {
                return null;
            }

            @Override
            public String getId() {
                return "";
            }

            @Override
            public Claims setId(String jti) {
                return null;
            }

            @Override
            public <T> T get(String claimName, Class<T> requiredType) {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean containsKey(Object key) {
                return false;
            }

            @Override
            public boolean containsValue(Object value) {
                return false;
            }

            @Override
            public Object get(Object key) {
                return null;
            }

            @Override
            public Object put(String key, Object value) {
                return null;
            }

            @Override
            public Object remove(Object key) {
                return null;
            }

            @Override
            public void putAll(Map<? extends String, ?> m) {

            }

            @Override
            public void clear() {

            }

            @Override
            public Set<String> keySet() {
                return Set.of();
            }

            @Override
            public Collection<Object> values() {
                return List.of();
            }

            @Override
            public Set<Entry<String, Object>> entrySet() {
                return Set.of();
            }
        };
        Mockito.when(extractAllClaims.extract(token)).thenReturn(claims);
        var result = extractClaim.extract(token, Claims::getSubject);
        Assertions.assertThat(result).isNotNull();
    }
}
