package com.goatspec.application.useCases.implementations.specialization;

import com.goatspec.application.gateways.specialization.ISpecializationGateway;
import com.goatspec.application.gateways.user.IUserGateway;
import com.goatspec.application.useCases.contracts.specialization.ICreateSpecializationUseCase;
import com.goatspec.domain.entities.specialization.Specialization;
import com.goatspec.domain.entities.user.User;
import com.goatspec.domain.exceptions.UnauthorizedException;

public class CreateSpecializationUseCase implements ICreateSpecializationUseCase {
    private final IUserGateway userGateway;
    private final ISpecializationGateway specializationGateway;

    public CreateSpecializationUseCase(IUserGateway userGateway, ISpecializationGateway specializationGateway) {
        this.userGateway = userGateway;
        this.specializationGateway = specializationGateway;
    }

    @Override
    public void create(Specialization specialization) {
        User user = this.userGateway.findUserById(specialization.userId());
        if (user == null) throw new UnauthorizedException("Something went wrong! please try again later!");
        this.specializationGateway.create(specialization);
    }
}
