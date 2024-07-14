package com.goatspec.application.useCases.implementations.authentication;

import com.goatspec.application.gateways.authentication.IAuthenticationGateway;
import com.goatspec.application.useCases.contracts.authentication.IGetLoggedUserInfoUseCase;
import com.goatspec.domain.entities.authentication.UserInfo;

public class GetLoggedUserInfoUseCase implements IGetLoggedUserInfoUseCase {
    private final IAuthenticationGateway authenticationGateway;

    public GetLoggedUserInfoUseCase(IAuthenticationGateway authenticationGateway) {
        this.authenticationGateway = authenticationGateway;
    }

    @Override
    public UserInfo get() {
        return this.authenticationGateway.getLoggedUserInfo();
    }
}
