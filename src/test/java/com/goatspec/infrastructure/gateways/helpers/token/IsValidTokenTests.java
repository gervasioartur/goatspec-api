package com.goatspec.infrastructure.gateways.helpers.token;

import com.goatspec.domain.Enums.GenderEnum;
import com.goatspec.domain.Enums.RoleEnum;
import com.goatspec.domain.entities.user.User;
import com.goatspec.infrastructure.persisntence.entities.RoleEntity;
import com.goatspec.infrastructure.persisntence.entities.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.Date;

@SpringBootTest
class IsValidTokenTests {
    @Autowired
    private IsValidToken isValidToken;
    @MockBean
    private GetUsernameFromToken getUsernameFromToken;
    @MockBean
    private IsTokenExpired isTokenExpired;


    private UserEntity userEntityFactory() {
        User toCreateUserDomainObject = new User("any_cpf", "any_email", "any_registration", "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "any_password");

        RoleEntity savedRoleEntity = RoleEntity.builder().name("any_name").active(true).build();
        return UserEntity
                .builder()
                .cpf(toCreateUserDomainObject.cpf())
                .email(toCreateUserDomainObject.email())
                .registration(toCreateUserDomainObject.registration())
                .name(toCreateUserDomainObject.name())
                .dateOfBirth(toCreateUserDomainObject.dateOfBirth())
                .gender(toCreateUserDomainObject.gender())
                .password(toCreateUserDomainObject.password())
                .roles(Collections.singletonList(savedRoleEntity))
                .active(true)
                .build();
    }

    @Test
    @DisplayName("Should return false if the username is not equal to user details name")
    void shouldReturnFalseIfUsernameIsNotEqualsToUserDetailsName() {
        String token = "any_token";
        String username = "any_username_returned";

        Mockito.when(getUsernameFromToken.get(token)).thenReturn(username);

        boolean result = isValidToken.isValid(token, this.userEntityFactory());
        Assertions.assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Should return false if isTokenExpired returns true")
    void shouldReturnTurnFalseIfIsTokenExpiredReturnsTrue() {
        String token = "any_token";
        UserEntity savedUser = this.userEntityFactory();

        Mockito.when(getUsernameFromToken.get(token)).thenReturn(savedUser.getUsername());
        Mockito.when(isTokenExpired.isTokenExpired(token)).thenReturn(true);

        boolean result = isValidToken.isValid(token, savedUser);
        Assertions.assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Should return true on validation success")
    void shouldReturnTurnTrueOnValidationSuccess() {
        String token = "any_token";
        UserEntity savedUser = this.userEntityFactory();

        Mockito.when(getUsernameFromToken.get(token)).thenReturn(savedUser.getUsername());
        Mockito.when(isTokenExpired.isTokenExpired(token)).thenReturn(false);

        boolean result = isValidToken.isValid(token, savedUser);
        Assertions.assertThat(result).isTrue();
    }
}
