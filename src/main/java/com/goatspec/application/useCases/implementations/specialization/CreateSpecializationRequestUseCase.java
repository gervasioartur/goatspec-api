package com.goatspec.application.useCases.implementations.specialization;

import com.goatspec.application.gateways.user.IUserGateway;
import com.goatspec.application.useCases.contracts.specialization.ICreateSpecializationRequestUseCase;
import com.goatspec.domain.entities.specialization.SpecializationRequest;
import com.goatspec.domain.entities.user.User;
import com.goatspec.domain.exceptions.UnauthorizedException;
import org.apache.coyote.BadRequestException;

public class CreateSpecializationRequestUseCase implements ICreateSpecializationRequestUseCase {
    private final IUserGateway userGateway;

    public CreateSpecializationRequestUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public void create(SpecializationRequest specializationRequest) {
        User user =  this.userGateway.findUserByCpf(specializationRequest.cpf());
        if (user == null) throw new UnauthorizedException("Something went wrong! please try again later!");
    }
}
