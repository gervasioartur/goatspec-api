package com.goatspec.infrastructure.gateways.helpers.token;


import com.goatspec.infrastructure.gateways.helpers.security.SingKey;
import io.jsonwebtoken.Jwts;


public class ValidateToken {
    public void validate(final String token) {
        Jwts.parserBuilder().setSigningKey(new SingKey().getSignKey()).build().parseClaimsJws(token);
    }
}
