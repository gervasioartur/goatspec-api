package com.goatspec.application.gateways.authentication;

public interface IAuthenticationGateway {
    String authenticate(String username, String password);
}
