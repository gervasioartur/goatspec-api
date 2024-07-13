package com.goatspec.infrastructure.gateways;

import com.goatspec.application.gateways.IUserGateway;
import com.goatspec.domain.entities.User;

public class UserGateway implements IUserGateway {

    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public User findUserByCpf(String cpf) {
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        return null;
    }

    @Override
    public User findUserByRegistration(String registration) {
        return null;
    }
}
