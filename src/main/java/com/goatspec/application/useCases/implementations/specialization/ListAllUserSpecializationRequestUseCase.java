package com.goatspec.application.useCases.implementations.specialization;

import com.goatspec.application.gateways.specialization.ISpecializationRequestGateway;
import com.goatspec.application.useCases.contracts.specialization.IListAllUserSpecializationRequestUseCase;
import com.goatspec.domain.entities.specialization.SpecializationRequestInfo;

import java.util.List;
import java.util.UUID;

public class ListAllUserSpecializationRequestUseCase implements IListAllUserSpecializationRequestUseCase {
    private final ISpecializationRequestGateway specializationRequestGateway;

    public ListAllUserSpecializationRequestUseCase(ISpecializationRequestGateway specializationRequestGateway) {
        this.specializationRequestGateway = specializationRequestGateway;
    }

    @Override
    public List<SpecializationRequestInfo> list(UUID userId) {
        return this.specializationRequestGateway.getAllByUserId(userId);
    }
}
