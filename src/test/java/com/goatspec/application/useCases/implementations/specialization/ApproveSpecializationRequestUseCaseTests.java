package com.goatspec.application.useCases.implementations.specialization;

import com.goatspec.application.gateways.email.ISendEmailGateway;
import com.goatspec.application.gateways.specialization.ISpecializationGateway;
import com.goatspec.application.useCases.contracts.specialization.IApproveSpecializationRequestUseCase;
import com.goatspec.domain.Enums.SpeciaiizationStatusEnum;
import com.goatspec.domain.entities.email.SendEmailParams;
import com.goatspec.domain.entities.specialization.SpecializationAndUser;
import com.goatspec.mocks.Mocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

@SpringBootTest
class ApproveSpecializationRequestUseCaseTests {
    private IApproveSpecializationRequestUseCase approveSpecializationRequestUseCase;
    @MockBean
    private ISpecializationGateway specializationGateway;
    @Mock
    private ISendEmailGateway sendEmailGateway;

    @BeforeEach
    void setUp() {
        approveSpecializationRequestUseCase = new ApproveSpecializationRequestUseCase(
                specializationGateway,
                sendEmailGateway);
    }

    @Test
    @DisplayName("Should approve Specialization request")
    void shouldApproveSpecializationRequest() {
        UUID specializationId = UUID.randomUUID();

        SpecializationAndUser result = Mocks.specializationAndUserFactory(SpeciaiizationStatusEnum.APPROVED.getValue());
        SendEmailParams sendEmailParams = Mocks.sendApprovedEmailParamsFactory(result);

        Mockito.when(this.specializationGateway.approve(specializationId)).thenReturn(result);
        Mockito.doNothing().when(this.sendEmailGateway).send(sendEmailParams);

        this.approveSpecializationRequestUseCase.approve(specializationId);

        Mockito.verify(this.specializationGateway, Mockito.times(1)).approve(specializationId);
        Mockito.verify(this.sendEmailGateway, Mockito.times(1)).send(sendEmailParams);
    }
}
