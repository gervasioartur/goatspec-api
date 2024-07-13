package com.goatspec.application.useCases.implementations.authentication;

import com.goatspec.application.gateways.authentication.IAuthenticationGateway;
import com.goatspec.application.gateways.user.IUserGateway;
import com.goatspec.application.useCases.contracts.authentication.ISinginUseCase;
import com.goatspec.domain.entities.user.User;
import com.goatspec.domain.entities.user.UserAccount;
import com.goatspec.domain.exceptions.BusinessException;
import com.goatspec.domain.exceptions.UnauthorizedException;
import com.goatspec.domain.exceptions.UnexpectedException;

public class SinginUseCase implements ISinginUseCase {
    private final IUserGateway userGateway;
    private final IAuthenticationGateway authenticationGateway;

    public SinginUseCase(IUserGateway userGateway, IAuthenticationGateway authenticationGateway) {
        this.userGateway = userGateway;
        this.authenticationGateway = authenticationGateway;
    }

    @Override
    public UserAccount singin(String cpf, String password) {
        User user = userGateway.findUserByCpf(cpf);
        if (user == null) throw  new UnauthorizedException("Bad credentials.");
        String accessToken = this.authenticationGateway.authenticate(cpf,password);
        return new UserAccount(accessToken);
    }
}
