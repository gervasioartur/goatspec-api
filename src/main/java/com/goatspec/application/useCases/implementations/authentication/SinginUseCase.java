package com.goatspec.application.useCases.implementations.authentication;

import com.goatspec.application.gateways.user.IUserGateway;
import com.goatspec.application.useCases.contracts.authentication.ISinginUseCase;
import com.goatspec.domain.entities.user.User;
import com.goatspec.domain.entities.user.UserAccount;
import com.goatspec.domain.exceptions.BusinessException;

public class SinginUseCase implements ISinginUseCase {
    private final IUserGateway userGateway;

    public SinginUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public UserAccount singin(String cpf, String password) {
        User user = userGateway.findUserByCpf(cpf);
        if (user == null) throw  new BusinessException("Bad credentials");
        return null;
    }
}
