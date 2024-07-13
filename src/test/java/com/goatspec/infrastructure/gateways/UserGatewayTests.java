package com.goatspec.infrastructure.gateways;

import com.goatspec.application.gateways.IUserGateway;
import com.goatspec.domain.Enums.GenderEnum;
import com.goatspec.domain.Enums.RoleEnum;
import com.goatspec.domain.entities.User;
import com.goatspec.infrastructure.gateways.mappers.UserEntityMapper;
import com.goatspec.infrastructure.persisntence.entoties.RoleEntity;
import com.goatspec.infrastructure.persisntence.entoties.UserEntity;
import com.goatspec.infrastructure.persisntence.repositories.IRoleRepository;
import com.goatspec.infrastructure.persisntence.repositories.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.Date;

@SpringBootTest
class UserGatewayTests {
    private IUserGateway userGateway;
    @MockBean
    private IUserRepository userRepository;
    @MockBean
    private UserEntityMapper userEntityMapper;
    @MockBean
    private IRoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        userGateway = new UserGateway(userRepository, userEntityMapper, roleRepository);
    }

    @Test
    @DisplayName("Should return user domain object")
    void shouldReturnUserDomainObject() {
        User toCreateUserDomainObject = new User("any_cpf", "any_email", "any_registration", "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "any_password");
        UserEntity toSaveUserEntity = UserEntity
                .builder()
                .cpf(toCreateUserDomainObject.cpf())
                .email(toCreateUserDomainObject.email())
                .registration(toCreateUserDomainObject.registration())
                .name(toCreateUserDomainObject.name())
                .dateOfBirth(toCreateUserDomainObject.dateOfBirth())
                .gender(toCreateUserDomainObject.gender())
                .password(toCreateUserDomainObject.password())
                .build();

        RoleEntity savedRoleEntity = RoleEntity.builder().name("any_name").build();
        UserEntity saveUserEntity = UserEntity
                .builder()
                .cpf(toCreateUserDomainObject.cpf())
                .email(toCreateUserDomainObject.email())
                .registration(toCreateUserDomainObject.registration())
                .name(toCreateUserDomainObject.name())
                .dateOfBirth(toCreateUserDomainObject.dateOfBirth())
                .gender(toCreateUserDomainObject.gender())
                .password(toCreateUserDomainObject.password())
                .roles(Collections.singletonList(savedRoleEntity))
                .build();

        Mockito.when(this.userEntityMapper.toUserEntity(toCreateUserDomainObject)).thenReturn(toSaveUserEntity);
        Mockito.when(this.roleRepository.findByName(toCreateUserDomainObject.role())).thenReturn(savedRoleEntity);
        Mockito.when(this.userRepository.save(toSaveUserEntity)).thenReturn(saveUserEntity);
        Mockito.when(this.userEntityMapper.toUserDomainObject(saveUserEntity, savedRoleEntity)).thenReturn(toCreateUserDomainObject);

        userGateway.create(toCreateUserDomainObject);

        Mockito.verify(this.userEntityMapper, Mockito.times(1)).toUserEntity(toCreateUserDomainObject);
        Mockito.verify(this.roleRepository, Mockito.times(1)).findByName(toCreateUserDomainObject.role());
        Mockito.verify(this.userRepository, Mockito.times(1)).save(toSaveUserEntity);
        Mockito.verify(this.userEntityMapper, Mockito.times(1)).toUserDomainObject(saveUserEntity, savedRoleEntity);
    }
}
