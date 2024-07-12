package com.goatspec.useCases.implementations;

import com.goatspec.domain.Enums.GenderEnum;
import com.goatspec.domain.Enums.RoleEnum;
import com.goatspec.domain.entities.User;
import com.goatspec.domain.exceptions.BusinessException;
import com.goatspec.gateways.IUserGateway;
import com.goatspec.useCases.contracts.ICreateUserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;

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
        User toCreateUser = new User("any_create_cpf","any_email","any_registration","any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue());
        User createdUser = new User("any_create_cpf","any_create_email","any_create_registration","aany_create_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue());

        Mockito.when(this.userGateway.findUserByCpf(toCreateUser.cpf())).thenReturn(createdUser);
        Throwable exception = Assertions.catchThrowable(() -> this.createUserService.create(toCreateUser) );

        Assertions.assertThat(exception).isInstanceOf(BusinessException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("The CPF is already in use. Please try to sing in with credentials.");
        Mockito.verify(this.userGateway, Mockito.times(1)).findUserByCpf(toCreateUser.cpf());
    }
}
