package com.goatspec.application.useCases.implementations.specialization;

import com.goatspec.application.gateways.user.IUserGateway;
import com.goatspec.application.useCases.contracts.specialization.ICreateSpecializationRequestUseCase;
import com.goatspec.domain.entities.specialization.SpecializationRequest;
import com.goatspec.domain.exceptions.BusinessException;
import com.goatspec.domain.exceptions.UnauthorizedException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
class CreateSpecializationRequestUseCaseTests {
    private ICreateSpecializationRequestUseCase createSpecializationRequestUseCase;
    @MockBean
    private IUserGateway userGateway;

    @BeforeEach
    void setUp() {
        this.createSpecializationRequestUseCase = new
                CreateSpecializationRequestUseCase(userGateway);
    }

    @Test
    @DisplayName("Should throw unauthorized exception if user does not exist")
    void shouldHowBusinessExceptionIfUserDoesNotExist() {
        SpecializationRequest specializationRequest = new
                SpecializationRequest("any_cpf","any_area","any_type",2,new BigDecimal("20"));

        Mockito.when(this.userGateway.findUserByCpf(specializationRequest.cpf())).thenReturn(null);

        Throwable exception = Assertions.catchThrowable(() -> this.createSpecializationRequestUseCase.create(specializationRequest));

        Assertions.assertThat(exception).isInstanceOf(UnauthorizedException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Something went wrong! please try again later!");
        Mockito.verify(this.userGateway, Mockito.times(1)).findUserByCpf(specializationRequest.cpf());
    }
}
