package com.goatspec.application.useCases.implementations.specialization;

import com.goatspec.application.gateways.specialization.ISpecializationGateway;
import com.goatspec.application.useCases.contracts.specialization.IApproveSpecializationRequestUseCase;
import com.goatspec.domain.entities.specialization.Specialization;
import com.goatspec.domain.exceptions.NotFoundException;
import com.goatspec.infrastructure.persisntence.entities.SpecializationEntity;
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
class ApproveSpecializationRequestUseCaseTests {
    private IApproveSpecializationRequestUseCase approveSpecializationRequestUseCase;
    @MockBean
    private ISpecializationGateway specializationGateway;

    @BeforeEach
    void setUp() {
        approveSpecializationRequestUseCase = new ApproveSpecializationRequestUseCase(specializationGateway);
    }

    @Test
    @DisplayName("Should throw not found exception if the Specialization request does not exist")
    void shouldThrowNotFoundExceptionIfTheSpecializationRequestDoesNotExist() {
        UUID specializationRequestId = UUID.randomUUID();

        Mockito.when(this.specializationGateway.findById(specializationRequestId)).thenReturn(null);

        Throwable exception = Assertions.catchThrowable(()-> this.approveSpecializationRequestUseCase.approve(specializationRequestId));

        Assertions.assertThat(exception).isInstanceOf(NotFoundException.class);
        Assertions.assertThat(exception.getMessage()).contains("Couldn't find Specialization request.");
        Mockito.verify(this.specializationGateway, Mockito.times(1)).findById(specializationRequestId);
    }

    @Test
    @DisplayName("Should approve Specialization request")
    void shouldApproveSpecializationRequest() {
        Specialization createdSpecialization = Mocks.specializationDomainObjectFactory();

        Mockito.when(this.specializationGateway.findById(createdSpecialization.userId())).thenReturn(createdSpecialization);
        Mockito.doNothing().when(this.specializationGateway).approve(createdSpecialization);

        this.approveSpecializationRequestUseCase.approve(createdSpecialization.userId());

        Mockito.verify(this.specializationGateway, Mockito.times(1)).findById(createdSpecialization.userId());
        Mockito.verify(this.specializationGateway, Mockito.times(1)).approve(createdSpecialization);
    }
}
