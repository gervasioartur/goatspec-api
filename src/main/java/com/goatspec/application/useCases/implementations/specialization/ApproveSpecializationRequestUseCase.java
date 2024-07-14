package com.goatspec.application.useCases.implementations.specialization;

import com.goatspec.application.gateways.specialization.ISpecializationGateway;
import com.goatspec.application.useCases.contracts.specialization.IApproveSpecializationRequestUseCase;
import com.goatspec.domain.entities.specialization.Specialization;
import com.goatspec.domain.exceptions.NotFoundException;

import java.util.UUID;

public class ApproveSpecializationRequestUseCase implements IApproveSpecializationRequestUseCase {
   private final ISpecializationGateway specializationGateway;

    public ApproveSpecializationRequestUseCase(ISpecializationGateway specializationGateway) {
        this.specializationGateway = specializationGateway;
    }

    @Override
    public void approve(UUID specializationId) {
        Specialization specialization = specializationGateway.findById(specializationId);
        if (specialization == null) throw new NotFoundException("Couldn't find Specialization request.");
        this.specializationGateway.approve(specialization);
    }
}
