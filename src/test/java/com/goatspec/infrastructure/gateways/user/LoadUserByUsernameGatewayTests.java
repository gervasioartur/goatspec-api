package com.goatspec.infrastructure.gateways.user;

import com.goatspec.domain.Enums.GenderEnum;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@SpringBootTest
class LoadUserByUsernameGatewayTests {
    private LoadUserByUsernameGateway loadUserByUsernameGateway;

    @MockBean
    private IUserRepository userRepository;

    @BeforeEach
    void setup() {
        this.loadUserByUsernameGateway = new LoadUserByUsernameGateway(userRepository);
    }

    @Test
    @DisplayName("Should throw UsernameNotFoundException is the user does not exists")
    void shouldThrowUsernameNotFoundException() {
        String username = "any_username";
        Mockito.when(this.userRepository.findByCpfAndActive(username, true)).thenReturn(Optional.empty());
        Throwable exception = Assertions.catchThrowable(() -> this.loadUserByUsernameGateway.loadUserByUsername(username));
        Assertions.assertThat(exception).isInstanceOf(UsernameNotFoundException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("User not found with username: " + username);
    }

    @Test
    @DisplayName("Should return user")
    void shouldReturnUser() {
        String username = "any_username";
        RoleEntity savedRoleEntity = RoleEntity.builder().name("any_name").active(true).build();

        UserEntity savedUserEntity = UserEntity
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


        Mockito.when(this.userRepository.findByCpfAndActive(username, true)).thenReturn(Optional.of(savedUserEntity));

        UserDetails result = this.loadUserByUsernameGateway.loadUserByUsername(username);

        Assertions.assertThat(result.getUsername()).isEqualTo(savedUserEntity.getUsername());
        Assertions.assertThat(result.getPassword()).isEqualTo(savedUserEntity.getPassword());
        Assertions.assertThat(result.getAuthorities()).isEqualTo(savedUserEntity.getAuthorities());
        Assertions.assertThat(result.isAccountNonExpired()).isTrue();
        Assertions.assertThat(result.isAccountNonLocked()).isTrue();
        Assertions.assertThat(result.isCredentialsNonExpired()).isTrue();
        Assertions.assertThat(result.isEnabled()).isTrue();
    }
}
