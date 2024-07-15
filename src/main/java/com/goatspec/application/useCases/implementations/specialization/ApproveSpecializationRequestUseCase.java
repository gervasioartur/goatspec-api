package com.goatspec.application.useCases.implementations.specialization;

import com.goatspec.application.gateways.email.ISendEmailGateway;
import com.goatspec.application.gateways.specialization.ISpecializationRequestGateway;
import com.goatspec.application.useCases.contracts.specialization.IApproveSpecializationRequestUseCase;
import com.goatspec.domain.entities.email.SendEmailParams;
import com.goatspec.domain.entities.specialization.SpecializationRequestInfo;

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
        SpecializationRequestInfo result = this.specializationGateway.approve(specializationId);
        SendEmailParams sendEmailParams = new SendEmailParams(result.userEmail(),
                "Feedback on Specialization Request", "Congratulations! " + result.userName() + "\n" +
                "Your specialization request for " + result.type() + " on area " + result.area() + " has been successfully approved.");
        this.sendEmailGateway.send(sendEmailParams);
    }
}
