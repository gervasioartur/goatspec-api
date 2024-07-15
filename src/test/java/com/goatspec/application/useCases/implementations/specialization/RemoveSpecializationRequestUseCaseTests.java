package com.goatspec.application.useCases.implementations.specialization;

import com.goatspec.application.gateways.specialization.ISpecializationRequestGateway;
import com.goatspec.application.useCases.contracts.specialization.IRemoveSpecializationRequestUseCase;
import com.goatspec.domain.Enums.SpecializationRequestStatusEnum;
import com.goatspec.domain.entities.specialization.SpecializationRequestInfo;
import com.goatspec.domain.exceptions.BusinessException;
import com.goatspec.mocks.Mocks;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

@SpringBootTest
public class RemoveSpecializationRequestUseCaseTests {
    private IRemoveSpecializationRequestUseCase removeSpecializationRequestUseCase;
    @MockBean
    private ISpecializationRequestGateway specializationRequestGateway;

    @BeforeEach
    void setUp() {
        this.removeSpecializationRequestUseCase = new RemoveSpecializationRequestUseCase(specializationRequestGateway);
    }

    @Test
    @DisplayName("Should throw business exception if specialization status is different of PENDING")
    void shouldThrowBusinessExceptionIfSpecializationStatusIsDifferentOfPending() {
        UUID specializationId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();


        SpecializationRequestInfo result = Mocks.specializationInfoFactory(SpecializationRequestStatusEnum.APPROVED.getValue());
        Mockito.when(this.specializationRequestGateway.findByIdAndUserId(specializationId, userId)).thenReturn(result);

        Throwable exception = Assertions.catchThrowable(() -> this.removeSpecializationRequestUseCase.remove(specializationId, userId));

        Assertions.assertThat(exception).isInstanceOf(BusinessException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("You can only remove specialization request on pending status.");
        Mockito.verify(this.specializationRequestGateway, Mockito.times(1)).findByIdAndUserId(specializationId, userId);
    }

    @Test
    @DisplayName("Should remove specialization request")
    void shouldRemoveSpecializationRequest() {
        UUID specializationId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        SpecializationRequestInfo result = Mocks.specializationInfoFactory(SpecializationRequestStatusEnum.PENDING.getValue());

        Mockito.when(this.specializationRequestGateway.findByIdAndUserId(specializationId, userId)).thenReturn(result);
        Mockito.doNothing().when(this.specializationRequestGateway).remove(specializationId);

        this.removeSpecializationRequestUseCase.remove(specializationId, userId);

        Mockito.verify(this.specializationRequestGateway, Mockito.times(1)).findByIdAndUserId(specializationId, userId);
        Mockito.verify(this.specializationRequestGateway, Mockito.times(1)).remove(specializationId);
    }
}
