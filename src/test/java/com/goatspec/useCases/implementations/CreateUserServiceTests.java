package com.goatspec.useCases.implementations;

import com.goatspec.domain.Enums.GenderEnum;
import com.goatspec.domain.Enums.RoleEnum;
import com.goatspec.domain.entities.User;
import com.goatspec.domain.exceptions.BusinessException;
import com.goatspec.gateways.IUserGateway;
import com.goatspec.useCases.contracts.ICreateUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@SpringBootTest
class CreateUserServiceTests {
    private ICreateUserService createUserService;

    @MockBean
    private IUserGateway userGateway;

    @BeforeEach
    void setUp() {
        this.createUserService = new CreateUserService(userGateway);
    }

    @Test
    @DisplayName("Should throw business exception if CPF is already in use")
    void shouldThrowExceptionIfCPFIsAlreadyInUse() {
        User toCreateUser = new User("any_create_cpf", "any_email", "any_registration", "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue());
        User createdUser = new User("any_create_cpf", "any_create_email", "any_create_registration", "any_create_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue());

        when(this.userGateway.findUserByCpf(toCreateUser.cpf())).thenReturn(createdUser);
        Throwable exception = catchThrowable(() -> this.createUserService.create(toCreateUser));

        assertThat(exception).isInstanceOf(BusinessException.class);
        assertThat(exception.getMessage()).isEqualTo("The CPF is already in use. Please try to sing in with credentials.");
        verify(this.userGateway, times(1)).findUserByCpf(toCreateUser.cpf());
    }

    @Test
    @DisplayName("Should throw business exception if email is already taken")
    void shouldThrowExceptionIfEmailIsAlreadyTaken() {
        User toCreateUser = new User("any_cpf", "any_create_email", "any_registration", "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue());
        User createdUser = new User("any_create_cpf", "any_create_email", "any_create_registration", "any_create_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue());

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
        User toCreateUser = new User("any_cpf", "any_email", "any_create_registration", "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue());
        User createdUser = new User("any_create_cpf", "any_create_email", "any_create_registration", "any_create_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue());

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
}
