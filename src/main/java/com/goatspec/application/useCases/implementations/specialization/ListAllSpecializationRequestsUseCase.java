package com.goatspec.application.useCases.implementations.specialization;

import com.goatspec.application.gateways.specialization.ISpecializationRequestGateway;
import com.goatspec.application.useCases.contracts.specialization.IListAllSpecializationRequestsUseCase;
import com.goatspec.domain.entities.specialization.SpecializationRequestInfo;

import java.util.List;

public class ListAllSpecializationRequestsUseCase implements IListAllSpecializationRequestsUseCase {
    private final ISpecializationRequestGateway specializationGateway;

    public ListAllSpecializationRequestsUseCase(ISpecializationRequestGateway specializationGateway) {
        this.specializationGateway = specializationGateway;
    }

    @Override
    public List<SpecializationRequestInfo> getAll() {
        return this.specializationGateway.getAll();
    }
}
