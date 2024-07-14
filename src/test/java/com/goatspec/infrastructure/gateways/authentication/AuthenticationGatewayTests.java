package com.goatspec.infrastructure.gateways.authentication;

import com.goatspec.domain.entities.authentication.UserInfo;
import com.goatspec.infrastructure.gateways.authetication.AuthenticationGateway;
import com.goatspec.infrastructure.gateways.helpers.token.GenerateToken;
import com.goatspec.infrastructure.gateways.mappers.UserEntityMapper;
import com.goatspec.infrastructure.persisntence.entities.RoleEntity;
import com.goatspec.infrastructure.persisntence.entities.UserEntity;
import com.goatspec.infrastructure.persisntence.repositories.IUserRepository;
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
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

@SpringBootTest
class AuthenticationGatewayTests {
    private AuthenticationGateway authenticationGateway;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private GenerateToken generateToken;
    @MockBean
    private IUserRepository userRepository;
    @MockBean
    private UserEntityMapper userEntityMapper;


    @BeforeEach
    void setUp() {
        this.authenticationGateway = new AuthenticationGateway
                (authenticationManager, generateToken, userRepository, userEntityMapper);
    }

    @Test
    @DisplayName("Should return user info")
    void shouldReturnUserInfo() {
        RoleEntity savedRoleEntity = RoleEntity.builder().name("any_name").active(true).build();

        UserEntity savedUserEntity = UserEntity
                .builder()
                .id(UUID.randomUUID())
                .cpf("any_cpf")
                .email("any_email")
                .registration("any_registration")
                .name("any_name")
                .dateOfBirth(new Date())
                .gender("any_gender")
                .password("any_password")
                .roles(Collections.singletonList(savedRoleEntity))
                .active(true)
                .build();

        String cpf = "any_cpf";

        UserInfo userInfoDomainObject = new UserInfo(savedUserEntity.getId(), savedUserEntity.getName(), savedUserEntity.getEmail(), savedUserEntity.getRegistration());

        Authentication authentication = new UsernamePasswordAuthenticationToken(cpf, "senha123");
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);


        Mockito.when(this.userRepository.findByCpfAndActive(cpf, true)).thenReturn(Optional.of(savedUserEntity));
        Mockito.when(this.userEntityMapper.toUserInfo(savedUserEntity)).thenReturn(userInfoDomainObject);

        UserInfo result = this.authenticationGateway.getLoggedUserInfo();

        Assertions.assertThat(result).isEqualTo(userInfoDomainObject);
        Mockito.verify(this.userRepository, Mockito.times(1)).findByCpfAndActive(cpf, true);
        Mockito.verify(this.userEntityMapper, Mockito.times(1)).toUserInfo(savedUserEntity);
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
