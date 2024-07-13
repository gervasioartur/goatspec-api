package com.goatspec.main.config;


import com.goatspec.domain.Enums.GenderEnum;
import com.goatspec.infrastructure.gateways.helpers.token.GetUsernameFromToken;
import com.goatspec.infrastructure.gateways.helpers.token.IsValidToken;
import com.goatspec.infrastructure.gateways.user.LoadUserByUsernameGateway;
import com.goatspec.infrastructure.persisntence.entities.RoleEntity;
import com.goatspec.infrastructure.persisntence.entities.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;

import static org.mockito.Mockito.*;

@SpringBootTest
class JwtAuthenticationFilterTests {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private LoadUserByUsernameGateway loadUserByUsernameGateway;
    @MockBean
    private GetUsernameFromToken getUsernameFromToken;
    @MockBean
    private IsValidToken isValidToken;
    @MockBean
    private HttpServletRequest request;
    @MockBean
    private HttpServletResponse response;
    @MockBean
    private FilterChain filterChain;

    @Test
    void doFilterInternal_withValidToken_shouldAuthenticateUser() throws ServletException, IOException {


        String token = "valid_token";

        String username = "any_user_name";

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(securityContext);


        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(getUsernameFromToken.get(token)).thenReturn(username);
        RoleEntity savedRoleEntity = RoleEntity.builder().name("any_name").active(true).build();

        UserDetails userDetails = UserEntity
                .builder()
                .cpf("any_cpf")
                .email("any_email")
                .registration("any_registration")
                .name("any_name")
                .dateOfBirth(new Date())
                .gender(GenderEnum.MALE.getValue())
                .password("any_password")
                .roles(Collections.singletonList(savedRoleEntity))
                .active(true)
                .build();


        when(this.loadUserByUsernameGateway.loadUserByUsername(username)).thenReturn(userDetails);
        when(isValidToken.isValid(token, userDetails)).thenReturn(true);
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verify(loadUserByUsernameGateway, times(1)).loadUserByUsername(username);
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilterInternal_withValidToken_shouldAuthenticateUserTokenInvalid() throws ServletException, IOException {
        String token = "valid_token";
        String username = "user";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(getUsernameFromToken.get(token)).thenReturn(username);
        RoleEntity savedRoleEntity = RoleEntity.builder().name("any_name").active(true).build();

        UserDetails userDetails = UserEntity
                .builder()
                .cpf("any_cpf")
                .email("any_email")
                .registration("any_registration")
                .name("any_name")
                .dateOfBirth(new Date())
                .gender(GenderEnum.MALE.getValue())
                .password("any_password")
                .roles(Collections.singletonList(savedRoleEntity))
                .active(true)
                .build();
        when(loadUserByUsernameGateway.loadUserByUsername(username)).thenReturn(userDetails);
        when(isValidToken.isValid(token, userDetails)).thenReturn(false);
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verify(loadUserByUsernameGateway, times(1)).loadUserByUsername(username);
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilterInternal_withInvalidToken_shouldNotAuthenticateUser() throws ServletException, IOException {
        String token = "invalid_token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(getUsernameFromToken.get(token)).thenReturn(null);
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verify(loadUserByUsernameGateway, never()).loadUserByUsername(anyString());
        verify(request, never()).setAttribute(anyString(), anyString());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilterInternal_withInvalidToken_shouldNotAuthenticateUserInvalid() throws ServletException, IOException {
        String token = "invalid_token";
        when(request.getHeader("Authorization")).thenReturn("NOT_Bearer " + token);
        when(getUsernameFromToken.get(token)).thenReturn(null);
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verify(loadUserByUsernameGateway, never()).loadUserByUsername(anyString());
        verify(request, never()).setAttribute(anyString(), anyString());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilterInternal_withInvalidToken_shouldNotAuthenticateUserEmpty() throws ServletException, IOException {
        String token = "invalid_token";
        when(request.getHeader("Authorization")).thenReturn("");
        when(getUsernameFromToken.get(token)).thenReturn(null);
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verify(loadUserByUsernameGateway, never()).loadUserByUsername(anyString());
        verify(request, never()).setAttribute(anyString(), anyString());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilterInternal_withInvalidToken_shouldNotAuthenticateUserFilterThrow() throws ServletException, IOException {
        String token = "invalid_token";
        when(request.getHeader("Authorization")).thenReturn("");
        when(getUsernameFromToken.get(token)).thenReturn(null);
        doThrow(ServletException.class).when(filterChain).doFilter(request, response);
        Throwable exception = Assertions.catchThrowable(() -> jwtAuthenticationFilter.doFilterInternal(request, response, filterChain));
        verify(loadUserByUsernameGateway, never()).loadUserByUsername(anyString());
        verify(request, never()).setAttribute(anyString(), anyString());
        verify(filterChain, times(1)).doFilter(request, response);
        Assertions.assertThat(exception).isInstanceOf(ServletException.class);
    }

    @Test
    void doFilterInternal_withInvalidToken_shouldNotAuthenticateUserFilterThrowIOException() throws ServletException, IOException {
        String token = "invalid_token";
        when(request.getHeader("Authorization")).thenReturn("");
        when(getUsernameFromToken.get(token)).thenReturn(null);
        doThrow(IOException.class).when(filterChain).doFilter(request, response);
        Throwable exception = Assertions.catchThrowable(() -> jwtAuthenticationFilter.doFilterInternal(request, response, filterChain));
        verify(loadUserByUsernameGateway, never()).loadUserByUsername(anyString());
        verify(request, never()).setAttribute(anyString(), anyString());
        verify(filterChain, times(1)).doFilter(request, response);
        Assertions.assertThat(exception).isInstanceOf(IOException.class);
    }
}
