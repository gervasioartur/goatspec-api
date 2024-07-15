package com.goatspec.application.useCases.implementations.specialization;

import com.goatspec.application.gateways.specialization.ISpecializationRequestGateway;
import com.goatspec.application.useCases.contracts.specialization.IRemoveSpecializationRequest;
import com.goatspec.domain.entities.specialization.SpecializationRequestInfo;
import com.goatspec.domain.exceptions.NotFoundException;

import java.util.UUID;

public class RemoveSpecializationRequest implements IRemoveSpecializationRequest {
    private ISpecializationRequestGateway specializationRequestGateway;

    public RemoveSpecializationRequest(ISpecializationRequestGateway specializationRequestGateway) {
        this.specializationRequestGateway = specializationRequestGateway;
    }

    @Override
    public void remove(UUID specializationId) {
        SpecializationRequestInfo result =  this.specializationRequestGateway.findById(specializationId);
        if (result == null) throw new NotFoundException("Specialization request not found.");
    }
}
