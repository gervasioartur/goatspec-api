package com.goatspec.infrastructure.gateways.mappers;

import com.goatspec.domain.Enums.GenderEnum;
import com.goatspec.domain.Enums.RoleEnum;
import com.goatspec.domain.entities.authentication.UserInfo;
import com.goatspec.domain.entities.user.User;
import com.goatspec.infrastructure.persisntence.entities.RoleEntity;
import com.goatspec.infrastructure.persisntence.entities.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class UserEntityMapperTests {
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

    @Test
    @DisplayName("Should return user domain object")
    void shouldReturnUserDomainObject() {
        User toCreateUserDomainObject = new User("any_cpf", "any_email", "any_create_registration", "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "any_password");

        RoleEntity savedRoleEntity = RoleEntity.builder().name("any_name").active(true).build();
        UserEntity toCreateUserEntity = UserEntity
                .builder()
                .cpf(toCreateUserDomainObject.cpf())
                .email(toCreateUserDomainObject.email())
                .registration(toCreateUserDomainObject.registration())
                .name(toCreateUserDomainObject.name())
                .dateOfBirth(toCreateUserDomainObject.dateOfBirth())
                .gender(toCreateUserDomainObject.gender())
                .password(toCreateUserDomainObject.password())
                .build();

        toCreateUserDomainObject = mapper.toDomainObject(toCreateUserEntity, savedRoleEntity);

        Assertions.assertThat(toCreateUserEntity.getCpf()).isEqualTo(toCreateUserDomainObject.cpf());
        Assertions.assertThat(toCreateUserEntity.getEmail()).isEqualTo(toCreateUserDomainObject.email());
        Assertions.assertThat(toCreateUserEntity.getRegistration()).isEqualTo(toCreateUserDomainObject.registration());
        Assertions.assertThat(toCreateUserEntity.getName()).isEqualTo(toCreateUserDomainObject.name());
        Assertions.assertThat(toCreateUserEntity.getDateOfBirth()).isEqualTo(toCreateUserDomainObject.dateOfBirth());
        Assertions.assertThat(savedRoleEntity.getName()).isEqualTo(toCreateUserDomainObject.role());
        Assertions.assertThat(toCreateUserEntity.getPassword()).isEqualTo(toCreateUserDomainObject.password());
    }

    @Test
    @DisplayName("Should return user info")
    void shouldReturnUserInfo() {
        User toCreateUserDomainObject = new User("any_cpf", "any_email", "any_create_registration", "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "any_password");
        UserEntity toCreateUserEntity = UserEntity
                .builder()
                .cpf(toCreateUserDomainObject.cpf())
                .email(toCreateUserDomainObject.email())
                .registration(toCreateUserDomainObject.registration())
                .name(toCreateUserDomainObject.name())
                .dateOfBirth(toCreateUserDomainObject.dateOfBirth())
                .gender(toCreateUserDomainObject.gender())
                .password(toCreateUserDomainObject.password())
                .build();

        UserInfo result = this.mapper.toUserInfo(toCreateUserEntity);
        Assertions.assertThat(result.id()).isEqualTo(toCreateUserEntity.getId());
        Assertions.assertThat(result.name()).isEqualTo(toCreateUserEntity.getName());
        Assertions.assertThat(result.email()).isEqualTo(toCreateUserEntity.getEmail());
        Assertions.assertThat(result.registration()).isEqualTo(toCreateUserEntity.getRegistration());
    }
}
