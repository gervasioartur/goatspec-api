package com.goatspec.application.useCases.implementations.specialization;

import com.goatspec.application.gateways.specialization.ISpecializationRequestGateway;
import com.goatspec.application.useCases.contracts.specialization.IRemoveSpecializationRequest;
import com.goatspec.domain.Enums.SpecializationRequestStatusEnum;
import com.goatspec.domain.entities.specialization.SpecializationRequestInfo;
import com.goatspec.domain.exceptions.BusinessException;
import com.goatspec.domain.exceptions.NotFoundException;
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
public class RemoveSpecializationRequestTests {
    private IRemoveSpecializationRequest removeSpecializationRequest;
    @MockBean
    private ISpecializationRequestGateway specializationRequestGateway;

    @BeforeEach
    void setUp() {
        this.removeSpecializationRequest = new RemoveSpecializationRequest(specializationRequestGateway);
    }

    @Test
    @DisplayName("Should throw not found exception if specialization request does not exist")
    void shouldThrowNotFoundExceptionIfSpecializationRequestDoesNotExist() {
        UUID specializationId = UUID.randomUUID();

        Mockito.when(this.specializationRequestGateway.findById(specializationId)).thenReturn(null);

        Throwable exception = Assertions.catchThrowable(() -> this.removeSpecializationRequest.remove(specializationId));

        Assertions.assertThat(exception).isInstanceOf(NotFoundException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Specialization request not found.");
        Mockito.verify(this.specializationRequestGateway, Mockito.times(1)).findById(specializationId);
    }

    @Test
    @DisplayName("Should throw business exception if specialization status is different of PENDING")
    void shouldThrowBusinessExceptionIfSpecializationStatusIsDifferentOfPending() {
        UUID specializationId = UUID.randomUUID();
        SpecializationRequestInfo result = Mocks.specializationInfoFactory(SpecializationRequestStatusEnum.APPROVED.getValue());
        Mockito.when(this.specializationRequestGateway.findById(specializationId)).thenReturn(result);

        Throwable exception = Assertions.catchThrowable(() -> this.removeSpecializationRequest.remove(specializationId));

        Assertions.assertThat(exception).isInstanceOf(BusinessException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("You only can remove specialization request on pending status.");
        Mockito.verify(this.specializationRequestGateway, Mockito.times(1)).findById(specializationId);
    }
}
