package com.goatspec.infrastructure.gateways;

import com.goatspec.application.gateways.IUserGateway;
import com.goatspec.domain.Enums.GenderEnum;
import com.goatspec.domain.Enums.RoleEnum;
import com.goatspec.domain.entities.User;
import com.goatspec.infrastructure.gateways.mappers.UserEntityMapper;
import com.goatspec.infrastructure.persisntence.entities.RoleEntity;
import com.goatspec.infrastructure.persisntence.entities.UserEntity;
import com.goatspec.infrastructure.persisntence.repositories.IRoleRepository;
import com.goatspec.infrastructure.persisntence.repositories.IUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

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

        RoleEntity savedRoleEntity = RoleEntity.builder().name("any_name").active(true).build();
        UserEntity savedUserEntity = UserEntity
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

        Mockito.when(this.userEntityMapper.toUserEntity(toCreateUserDomainObject)).thenReturn(toSaveUserEntity);
        Mockito.when(this.roleRepository.findByNameAndActive(toCreateUserDomainObject.role(), true)).thenReturn(savedRoleEntity);
        Mockito.when(this.userRepository.save(toSaveUserEntity)).thenReturn(savedUserEntity);
        Mockito.when(this.userEntityMapper.toDomainObject(savedUserEntity, savedRoleEntity)).thenReturn(toCreateUserDomainObject);

        userGateway.create(toCreateUserDomainObject);

        Mockito.verify(this.userEntityMapper, Mockito.times(1)).toUserEntity(toCreateUserDomainObject);
        Mockito.verify(this.roleRepository, Mockito.times(1)).findByNameAndActive(toCreateUserDomainObject.role(), true);
        Mockito.verify(this.userRepository, Mockito.times(1)).save(toSaveUserEntity);
        Mockito.verify(this.userEntityMapper, Mockito.times(1)).toDomainObject(savedUserEntity, savedRoleEntity);
    }

    @Test
    @DisplayName("Should return null if user does not exist by CPF")
    void shouldReturnNullIfUserDoesNotExistByCpf() {
        String cpf = "any_cpf";
        Mockito.when(this.userRepository.findByCpfAndActive(cpf, true)).thenReturn(Optional.empty());
        User userDomainObject = this.userGateway.findUserByCpf(cpf);
        Assertions.assertThat(userDomainObject).isNull();
        Mockito.verify(this.userRepository, Mockito.times(1)).findByCpfAndActive(cpf, true);
    }

    @Test
    @DisplayName("Should return user domain object if exists by CPF")
    void shouldReturnUserDomainObjectIfExists() {
        String cpf = "any_cpf";

        User toCreateUserDomainObject = new User("any_cpf", "any_email", "any_registration", "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "any_password");

        RoleEntity savedRoleEntity = RoleEntity.builder().name("any_name").active(true).build();
        UserEntity savedUserEntity = UserEntity
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

        Mockito.when(this.userRepository.findByCpfAndActive(cpf, true)).thenReturn(Optional.of(savedUserEntity));
        Mockito.when(this.userEntityMapper.toDomainObject(savedUserEntity, savedRoleEntity)).thenReturn(toCreateUserDomainObject);

        User userDomainObject = this.userGateway.findUserByCpf(cpf);

        Assertions.assertThat(userDomainObject).isEqualTo(toCreateUserDomainObject);
        Mockito.verify(this.userRepository, Mockito.times(1)).findByCpfAndActive(cpf, true);
    }

    @Test
    @DisplayName("Should return null if user does not exist by email")
    void shouldReturnNullIfUserDoesNotExistByEmail() {
        String email = "any_email";
        Mockito.when(this.userRepository.findByEmailAndActive(email, true)).thenReturn(Optional.empty());
        User userDomainObject = this.userGateway.findUserByEmail(email);
        Assertions.assertThat(userDomainObject).isNull();
        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmailAndActive(email, true);
    }

    @Test
    @DisplayName("Should return user domain object if exists by email")
    void shouldReturnUserDomainObjectIfExistsByEmail() {
        String email = "any_email";

        User toCreateUserDomainObject = new User("any_cpf", "any_email", "any_registration", "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "any_password");

        RoleEntity savedRoleEntity = RoleEntity.builder().name("any_name").active(true).build();
        UserEntity savedUserEntity = UserEntity
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

        Mockito.when(this.userRepository.findByEmailAndActive(email, true)).thenReturn(Optional.of(savedUserEntity));
        Mockito.when(this.userEntityMapper.toDomainObject(savedUserEntity, savedRoleEntity)).thenReturn(toCreateUserDomainObject);

        User userDomainObject = this.userGateway.findUserByEmail(email);

        Assertions.assertThat(userDomainObject).isEqualTo(toCreateUserDomainObject);
        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmailAndActive(email, true);
    }

    @Test
    @DisplayName("Should return null if user does not exist by registration")
    void shouldReturnNullIfUserDoesNotExistByRegistration() {
        String registration = "any_registration";
        Mockito.when(this.userRepository.findByRegistrationAndActive(registration, true)).thenReturn(Optional.empty());
        User userDomainObject = this.userGateway.findUserByRegistration(registration);
        Assertions.assertThat(userDomainObject).isNull();
        Mockito.verify(this.userRepository, Mockito.times(1)).findByRegistrationAndActive(registration, true);
    }

    @Test
    @DisplayName("Should return user domain object if exists by registration")
    void shouldReturnUserDomainObjectIfExistsByRegistration() {
        String registration = "any_registration";

        User toCreateUserDomainObject = new User("any_cpf", "any_email", "any_registration", "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "any_password");

        RoleEntity savedRoleEntity = RoleEntity.builder().name("any_name").active(true).build();
        UserEntity savedUserEntity = UserEntity
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

        Mockito.when(this.userRepository.findByRegistrationAndActive(registration, true)).thenReturn(Optional.of(savedUserEntity));
        Mockito.when(this.userEntityMapper.toDomainObject(savedUserEntity, savedRoleEntity)).thenReturn(toCreateUserDomainObject);

        User userDomainObject = this.userGateway.findUserByRegistration(registration);

        Assertions.assertThat(userDomainObject).isEqualTo(toCreateUserDomainObject);
        Mockito.verify(this.userRepository, Mockito.times(1)).findByRegistrationAndActive(registration, true);

    }
}
