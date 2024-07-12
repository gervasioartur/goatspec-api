package com.goatspec.useCases.implementations;

import com.goatspec.domain.entities.User;
import com.goatspec.domain.entities.UserAccount;
import com.goatspec.domain.exceptions.BusinessException;
import com.goatspec.gateways.IUserGateway;
import com.goatspec.useCases.contracts.ICreateUserService;

public class CreateUserService implements ICreateUserService {
    private final IUserGateway userGateway;

    public CreateUserService(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public UserAccount create(User userDomainObject) {

        User userResult = this.userGateway.findUserByCpf(userDomainObject.cpf());
        if (userResult != null)
            throw new BusinessException("The CPF is already in use. Please try to sing in with credentials.");
        return null;
    }
}
