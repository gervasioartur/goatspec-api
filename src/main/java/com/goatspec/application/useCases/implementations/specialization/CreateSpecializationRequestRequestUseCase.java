package com.goatspec.application.useCases.implementations.specialization;

import com.goatspec.application.gateways.specialization.ISpecializationRequestGateway;
import com.goatspec.application.gateways.user.IUserGateway;
import com.goatspec.application.useCases.contracts.specialization.ICreateSpecializationRequestUseCase;
import com.goatspec.domain.entities.specialization.SpecializationRequest;
import com.goatspec.domain.entities.user.User;
import com.goatspec.domain.exceptions.UnauthorizedException;

public class CreateSpecializationRequestRequestUseCase implements ICreateSpecializationRequestUseCase {
    private final IUserGateway userGateway;
    private final ISpecializationRequestGateway specializationGateway;

    public CreateSpecializationRequestRequestUseCase(IUserGateway userGateway, ISpecializationRequestGateway specializationGateway) {
        this.userGateway = userGateway;
        this.specializationGateway = specializationGateway;
    }

    @Override
    public void create(SpecializationRequest specializationRequest) {
        User user = this.userGateway.findUserById(specializationRequest.userId());
        if (user == null) throw new UnauthorizedException("Something went wrong! please try again later!");
        this.specializationGateway.create(specializationRequest);
    }
}
