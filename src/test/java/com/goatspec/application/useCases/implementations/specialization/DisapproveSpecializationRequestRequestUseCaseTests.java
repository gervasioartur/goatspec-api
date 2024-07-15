package com.goatspec.application.useCases.implementations.specialization;

import com.goatspec.application.gateways.email.ISendEmailGateway;
import com.goatspec.application.gateways.specialization.ISpecializationRequestGateway;
import com.goatspec.application.useCases.contracts.specialization.IDisapproveSpecializationRequestUseCase;
import com.goatspec.domain.Enums.SpecializationRequestStatusEnum;
import com.goatspec.domain.entities.email.SendEmailParams;
import com.goatspec.domain.entities.specialization.SpecializationRequestInfo;
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
class DisapproveSpecializationRequestRequestUseCaseTests {
    private IDisapproveSpecializationRequestUseCase disapproveSpecializationRequestUseCase;
    @MockBean
    private ISpecializationRequestGateway specializationGateway;
    @Mock
    private ISendEmailGateway sendEmailGateway;

    @BeforeEach
    void setUp() {
        disapproveSpecializationRequestUseCase = new DisapproveSpecializationRequestUseCase(
                specializationGateway,
                sendEmailGateway);
    }

    @Test
    @DisplayName("Should approve Specialization request")
    void shouldApproveSpecializationRequest() {
        UUID specializationId = UUID.randomUUID();

        SpecializationRequestInfo result = Mocks.specializationInfoFactory(SpecializationRequestStatusEnum.APPROVED.getValue());
        SendEmailParams sendEmailParams = Mocks.sendDisapprovedEmailParamsFactory(result);

        Mockito.when(this.specializationGateway.disapprove(specializationId)).thenReturn(result);
        Mockito.doNothing().when(this.sendEmailGateway).send(sendEmailParams);

        this.disapproveSpecializationRequestUseCase.disapprove(specializationId);

        Mockito.verify(this.specializationGateway, Mockito.times(1)).disapprove(specializationId);
        Mockito.verify(this.sendEmailGateway, Mockito.times(1)).send(sendEmailParams);
    }
}
