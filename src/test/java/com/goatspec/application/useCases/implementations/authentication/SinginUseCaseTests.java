package com.goatspec.application.useCases.implementations.authentication;

import com.goatspec.application.gateways.user.IUserGateway;
import com.goatspec.application.useCases.contracts.authentication.ISinginUseCase;
import com.goatspec.domain.exceptions.BusinessException;
import com.goatspec.domain.exceptions.UnauthorizedException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
class SinginUseCaseTests {
    private ISinginUseCase singinUseCase;
    @MockBean
    private IUserGateway userGateway;

    @BeforeEach
    void setUp() {
        this.singinUseCase = new SinginUseCase(userGateway);
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

}
