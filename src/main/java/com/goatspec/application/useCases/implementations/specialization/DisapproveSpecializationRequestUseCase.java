package com.goatspec.application.useCases.implementations.specialization;

import com.goatspec.application.gateways.email.ISendEmailGateway;
import com.goatspec.application.gateways.specialization.ISpecializationRequestGateway;
import com.goatspec.application.useCases.contracts.specialization.IDisapproveSpecializationRequestUseCase;
import com.goatspec.domain.entities.email.SendEmailParams;
import com.goatspec.domain.entities.specialization.SpecializationRequestInfo;

import java.util.UUID;

public class DisapproveSpecializationRequestUseCase implements IDisapproveSpecializationRequestUseCase {
    private final ISpecializationRequestGateway specializationGateway;
    private final ISendEmailGateway sendEmailGateway;

    public DisapproveSpecializationRequestUseCase(ISpecializationRequestGateway specializationGateway,
                                                  ISendEmailGateway sendEmailGateway) {
        this.specializationGateway = specializationGateway;
        this.sendEmailGateway = sendEmailGateway;
    }

    @Override
    public void disapprove(UUID specializationId) {
        SpecializationRequestInfo result = this.specializationGateway.disapprove(specializationId);
        SendEmailParams sendEmailParams = new SendEmailParams(result.userEmail(),
                "Feedback on Specialization Request", "We sorry! " + result.userName() + "\n" +
                "Your specialization request for " + result.type() + " on area " + result.area() + " has been  disapproved.");
        this.sendEmailGateway.send(sendEmailParams);
    }
}
