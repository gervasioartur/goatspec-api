package com.goatspec.application.gateways;

public interface IAuthenticationGateway {
    String authenticate(String username, String password);
}
