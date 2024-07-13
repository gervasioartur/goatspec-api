package com.goatspec.application.useCases.implementations;

import com.goatspec.application.gateways.IAuthenticationGateway;
import com.goatspec.application.gateways.IPasswordEncoderGateway;
import com.goatspec.application.gateways.IRoleGateway;
import com.goatspec.application.gateways.IUserGateway;
import com.goatspec.application.useCases.contracts.ICreateUserService;
import com.goatspec.domain.Enums.GenderEnum;
import com.goatspec.domain.Enums.RoleEnum;
import com.goatspec.domain.entities.Role;
import com.goatspec.domain.entities.User;
import com.goatspec.domain.entities.UserAccount;
import com.goatspec.domain.exceptions.BusinessException;
import com.goatspec.domain.exceptions.UnexpectedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@SpringBootTest
class CreateUserUseCaseTests {
    private ICreateUserService createUserService;

    @MockBean
    private IUserGateway userGateway;
    @MockBean
    private IRoleGateway roleGateway;
    @MockBean
    private IPasswordEncoderGateway passwordEncoderGateway;
    @MockBean
    private IAuthenticationGateway authentication;

    @BeforeEach
    void setUp() {
        this.createUserService = new CreateUserUseCase(
                userGateway,
                roleGateway,
                passwordEncoderGateway,
                authentication);
    }

    @Test
    @DisplayName("Should throw business exception if CPF is already in use")
    void shouldThrowExceptionIfCPFIsAlreadyInUse() {
        User toCreateUser = new User("any_create_cpf", "any_email", "any_registration", "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "any_password");
        User createdUser = new User("any_create_cpf", "any_create_email", "any_create_registration", "any_create_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "any_created_password");

        when(this.userGateway.findUserByCpf(toCreateUser.cpf())).thenReturn(createdUser);

        Throwable exception = catchThrowable(() -> this.createUserService.create(toCreateUser));

        assertThat(exception).isInstanceOf(BusinessException.class);
        assertThat(exception.getMessage()).isEqualTo("The CPF is already in use. Please try to sing in with credentials.");
        verify(this.userGateway, times(1)).findUserByCpf(toCreateUser.cpf());
    }

    @Test
    @DisplayName("Should throw business exception if email is already taken")
    void shouldThrowExceptionIfEmailIsAlreadyTaken() {
        User toCreateUser = new User("any_cpf", "any_create_email", "any_registration", "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "any_password");
        User createdUser = new User("any_create_cpf", "any_create_email", "any_create_registration", "any_create_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "any_created_password");

        when(this.userGateway.findUserByCpf(toCreateUser.cpf())).thenReturn(null);
        when(this.userGateway.findUserByEmail(toCreateUser.email())).thenReturn(createdUser);

        Throwable exception = catchThrowable(() -> this.createUserService.create(toCreateUser));

        assertThat(exception).isInstanceOf(BusinessException.class);
        assertThat(exception.getMessage()).isEqualTo("The email is already in use. Please try another email.");
        verify(this.userGateway, times(1)).findUserByCpf(toCreateUser.cpf());
        verify(this.userGateway, times(1)).findUserByEmail(toCreateUser.email());
    }

    @Test
    @DisplayName("Should throw business exception if registration is already taken")
    void shouldThrowExceptionIfRegistrationIsAlreadyTaken() {
        User toCreateUser = new User("any_cpf", "any_email", "any_create_registration", "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "any_password");
        User createdUser = new User("any_create_cpf", "any_create_email", "any_create_registration", "any_create_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "any_created_password");

        when(this.userGateway.findUserByCpf(toCreateUser.cpf())).thenReturn(null);
        when(this.userGateway.findUserByEmail(toCreateUser.email())).thenReturn(null);
        when(this.userGateway.findUserByRegistration(toCreateUser.registration())).thenReturn(createdUser);

        Throwable exception = catchThrowable(() -> this.createUserService.create(toCreateUser));

        assertThat(exception).isInstanceOf(BusinessException.class);
        assertThat(exception.getMessage()).isEqualTo("The registration is already in use. Please try another registration.");
        verify(this.userGateway, times(1)).findUserByCpf(toCreateUser.cpf());
        verify(this.userGateway, times(1)).findUserByEmail(toCreateUser.email());
        verify(this.userGateway, times(1)).findUserByRegistration(toCreateUser.registration());
    }

    @Test
    @DisplayName("Should throw unexpected expcetion if user role does not existis")
    void shouldThrowUnexpectedExceptionIfUserRoleDoesNotExists() {
        User toCreateUser = new User("any_cpf", "any_email", "any_registration", "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "any_password");

        when(this.userGateway.findUserByCpf(toCreateUser.cpf())).thenReturn(null);
        when(this.userGateway.findUserByEmail(toCreateUser.email())).thenReturn(null);
        when(this.userGateway.findUserByRegistration(toCreateUser.registration())).thenReturn(null);
        when(this.roleGateway.findRoleByName(toCreateUser.role())).thenReturn(null);

        Throwable exception = catchThrowable(() -> this.createUserService.create(toCreateUser));

        assertThat(exception).isInstanceOf(UnexpectedException.class);
        assertThat(exception.getMessage()).isEqualTo("Something went wrong while saving the information. Please concat the administrator.");
        verify(this.userGateway, times(1)).findUserByCpf(toCreateUser.cpf());
        verify(this.userGateway, times(1)).findUserByEmail(toCreateUser.email());
        verify(this.userGateway, times(1)).findUserByRegistration(toCreateUser.registration());
        verify(this.roleGateway, times(1)).findRoleByName(toCreateUser.role());
    }

    @Test
    @DisplayName("Shlould return user account on success")
    void shouldReturnUserAccountOnSuccess() {
        User toCreateUser = new User("any_cpf", "any_email", "any_registration", "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "any_password");
        Role createdRole = new Role( "any_role");

        String encodedPassword = UUID.randomUUID().toString();
        String accessToken = UUID.randomUUID().toString();
        User createdUser = new User("any_cpf", "any_email", "any_registration", "any_name", toCreateUser.dateOfBirth(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), encodedPassword);


        when(this.userGateway.findUserByCpf(toCreateUser.cpf())).thenReturn(null);
        when(this.userGateway.findUserByEmail(toCreateUser.email())).thenReturn(null);
        when(this.userGateway.findUserByRegistration(toCreateUser.registration())).thenReturn(null);
        when(this.roleGateway.findRoleByName(toCreateUser.role())).thenReturn(createdRole);
        when(this.userGateway.create(createdUser)).thenReturn(createdUser);
        when(this.passwordEncoderGateway.encode(toCreateUser.password())).thenReturn(encodedPassword);
        when(this.userGateway.create(createdUser)).thenReturn(createdUser);
        when(this.authentication.authenticate(createdUser.cpf(), toCreateUser.password())).thenReturn(accessToken);

        UserAccount userAccount = this.createUserService.create(toCreateUser);

        assertThat(userAccount.accessToken()).isEqualTo(accessToken);
        verify(this.userGateway, times(1)).findUserByCpf(toCreateUser.cpf());
        verify(this.userGateway, times(1)).findUserByEmail(toCreateUser.email());
        verify(this.userGateway, times(1)).findUserByRegistration(toCreateUser.registration());
        verify(this.roleGateway, times(1)).findRoleByName(toCreateUser.role());
        verify(this.userGateway, times(1)).create(createdUser);
        verify(this.passwordEncoderGateway, times(1)).encode(toCreateUser.password());
        verify(this.userGateway, times(1)).create(createdUser);
        verify(this.authentication, times(1)).authenticate(createdUser.cpf(), toCreateUser.password());
    }
}
