package com.goatspec.application.useCases.implementations.specialization;

import com.goatspec.application.gateways.email.ISendEmailGateway;
import com.goatspec.application.gateways.specialization.ISpecializationGateway;
import com.goatspec.application.useCases.contracts.specialization.IApproveSpecializationRequestUseCase;
import com.goatspec.domain.entities.email.SendEmailParams;
import com.goatspec.domain.entities.specialization.SpecializationAndUser;

import java.util.UUID;

public class ApproveSpecializationRequestUseCase implements IApproveSpecializationRequestUseCase {
    private final ISpecializationGateway specializationGateway;
    private final ISendEmailGateway sendEmailGateway;

    public ApproveSpecializationRequestUseCase(ISpecializationGateway specializationGateway,
                                               ISendEmailGateway sendEmailGateway) {
        this.specializationGateway = specializationGateway;
        this.sendEmailGateway = sendEmailGateway;
    }

    @Override
    public void approve(UUID specializationId) {
        SpecializationAndUser result = this.specializationGateway.approve(specializationId);
        SendEmailParams sendEmailParams = new SendEmailParams(result.userInfo().email(),
                "Feedback on Specialization Request", "Congratulations!" + result.userInfo().name() + "\n" +
                "Your specialization request for " + result.specialization().type() + "on area " + result.specialization().area() + " has been successfully approved.");
        this.sendEmailGateway.send(sendEmailParams);
    }
}
