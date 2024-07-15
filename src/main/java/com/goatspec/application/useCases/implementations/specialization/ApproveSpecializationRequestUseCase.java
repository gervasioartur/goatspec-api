package com.goatspec.application.useCases.implementations.specialization;

import com.goatspec.application.gateways.email.ISendEmailGateway;
import com.goatspec.application.gateways.specialization.ISpecializationRequestGateway;
import com.goatspec.application.useCases.contracts.specialization.IApproveSpecializationRequestUseCase;
import com.goatspec.domain.entities.email.SendEmailParams;
import com.goatspec.domain.entities.specialization.SpecializationRequestAndUser;

import java.util.UUID;

public class ApproveSpecializationRequestUseCase implements IApproveSpecializationRequestUseCase {
    private final ISpecializationRequestGateway specializationGateway;
    private final ISendEmailGateway sendEmailGateway;

    public ApproveSpecializationRequestUseCase(ISpecializationRequestGateway specializationGateway,
                                               ISendEmailGateway sendEmailGateway) {
        this.specializationGateway = specializationGateway;
        this.sendEmailGateway = sendEmailGateway;
    }

    @Override
    public void approve(UUID specializationId) {
        SpecializationRequestAndUser result = this.specializationGateway.approve(specializationId);
        SendEmailParams sendEmailParams = new SendEmailParams(result.userInfo().email(),
                "Feedback on Specialization Request", "Congratulations!" + result.userInfo().name() + "\n" +
                "Your specialization request for " + result.specializationRequest().type() + "on area " + result.specializationRequest().area() + " has been successfully approved.");
        this.sendEmailGateway.send(sendEmailParams);
    }
}
