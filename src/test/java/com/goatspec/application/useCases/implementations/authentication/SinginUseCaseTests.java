package com.goatspec.application.useCases.implementations.authentication;

import com.goatspec.application.gateways.authentication.IAuthenticationGateway;
import com.goatspec.application.gateways.user.IUserGateway;
import com.goatspec.application.useCases.contracts.authentication.ISinginUseCase;
import com.goatspec.domain.Enums.GenderEnum;
import com.goatspec.domain.Enums.RoleEnum;
import com.goatspec.domain.entities.user.User;
import com.goatspec.domain.entities.user.UserAccount;
import com.goatspec.domain.exceptions.BusinessException;
import com.goatspec.domain.exceptions.UnauthorizedException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class SinginUseCaseTests {
    private ISinginUseCase singinUseCase;
    @MockBean
    private IUserGateway userGateway;
    @MockBean
    private IAuthenticationGateway  authenticationGateway;

    @BeforeEach
    void setUp() {
        this.singinUseCase = new SinginUseCase(userGateway, authenticationGateway);
    }

    @Test
    @DisplayName("Should throw unauthorized exception i the user does not exists")
    void shouldThrowBusinessExceptionIfUserDoesNotExist() {
        String cpf =  "any_cpf";
        String password = "any_password";
        Mockito.when(this.userGateway.findUserByCpf(cpf)).thenReturn(null);
        Throwable exception = Assertions.catchThrowable(() ->  this.singinUseCase.singin(cpf,password));
        Assertions.assertThat(exception).isInstanceOf(UnauthorizedException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Bad credentials.");
        Mockito.verify(this.userGateway, Mockito.times(1)).findUserByCpf(cpf);
    }

    @Test
    @DisplayName("Should return User Account on athentication success")
    void shouldReturnUserAccountOnAuthenticationSuccess() {
        User createdUserDomainObject = new User("any_cpf", "any_email", "any_registration", "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "any_password");
        String cpf =  "any_cpf";
        String password = "any_password";
        String accessToken = UUID.randomUUID().toString();

        Mockito.when(this.userGateway.findUserByCpf(cpf)).thenReturn(createdUserDomainObject);
        Mockito.when(this.authenticationGateway.authenticate(cpf,password)).thenReturn(accessToken);

        UserAccount userAccount =  this.singinUseCase.singin(cpf,password);

        Assertions.assertThat(userAccount.accessToken()).isEqualTo(accessToken);
        Mockito.verify(this.userGateway, Mockito.times(1)).findUserByCpf(cpf);
    }

}
