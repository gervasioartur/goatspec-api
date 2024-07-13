package com.goatspec.infrastructure.gateways.authentication;

import com.goatspec.infrastructure.gateways.authetication.AuthenticationGateway;
import com.goatspec.infrastructure.gateways.helpers.token.GenerateToken;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class AuthenticationGatewayTests {
    private AuthenticationGateway authenticationGateway;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private GenerateToken generateToken;

    @BeforeEach
    void setUp() {
        this.authenticationGateway = new AuthenticationGateway(authenticationManager, generateToken);
    }

    @Test
    @DisplayName("Should return accestoken")
    void shouldReturnAccessToken() {
        String username = "any_username";
        String password = "any_password";
        String accessToken = UUID.randomUUID().toString();

        Authentication authentication = new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of();
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return "";
            }
        };

        Mockito.when(this.authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        Mockito.when(this.generateToken.generate(username)).thenReturn(accessToken);

        String accessTokenResult = this.authenticationGateway.authenticate(username, password);

        Assertions.assertThat(accessTokenResult).isEqualTo(accessToken);
        Mockito.verify(this.authenticationManager).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
    }

}
