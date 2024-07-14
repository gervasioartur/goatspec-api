package com.goatspec.application.useCases.implementations.specialization;

import com.goatspec.application.gateways.specialization.ISpecializationGateway;
import com.goatspec.application.useCases.contracts.specialization.IListAllSpecializationRequestsUseCase;
import com.goatspec.domain.entities.specialization.SpecializationAndUser;

import java.util.List;

public class ListAllSpecializationRequestsUseCase implements IListAllSpecializationRequestsUseCase {
    private final ISpecializationGateway specializationGateway;

    public ListAllSpecializationRequestsUseCase(ISpecializationGateway specializationGateway) {
        this.specializationGateway = specializationGateway;
    }

    @Override
    public List<SpecializationAndUser> getAll() {
        return this.specializationGateway.getAll();
    }
}
