package com.goatspec.infrastructure.gateways.mappers;

import com.goatspec.domain.Enums.GenderEnum;
import com.goatspec.domain.Enums.RoleEnum;
import com.goatspec.domain.entities.User;
import com.goatspec.infrastructure.persisntence.entoties.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class UserEntityMapperTests {
    private final UserEntityMapper mapper = new UserEntityMapper();

    @Test
    @DisplayName("Should return a user entity")
    void shouldReturnUserEntity() {
        User toCreateUserDomainObject = new User("any_cpf", "any_email", "any_create_registration", "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "any_password");
        UserEntity toCreateUserEntity = mapper.toUserEntity(toCreateUserDomainObject);

        Assertions.assertThat(toCreateUserEntity.getCpf()).isEqualTo(toCreateUserDomainObject.cpf());
        Assertions.assertThat(toCreateUserEntity.getEmail()).isEqualTo(toCreateUserDomainObject.email());
        Assertions.assertThat(toCreateUserEntity.getRegistration()).isEqualTo(toCreateUserDomainObject.registration());
        Assertions.assertThat(toCreateUserEntity.getName()).isEqualTo(toCreateUserDomainObject.name());
        Assertions.assertThat(toCreateUserEntity.getDateOfBirth()).isEqualTo(toCreateUserDomainObject.dateOfBirth());
        Assertions.assertThat(toCreateUserEntity.getPassword()).isEqualTo(toCreateUserDomainObject.password());
    }
}
