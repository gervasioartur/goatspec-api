package com.goatspec.application.gateways.authentication;

import com.goatspec.domain.entities.authentication.UserInfo;

public interface IAuthenticationGateway {
    UserInfo getLoggedUserInfo();

    String authenticate(String username, String password);
}
