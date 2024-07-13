package com.goatspec.application.gateways.user;

import com.goatspec.domain.entities.user.User;

public interface IUserGateway {
    User create(User user);

    User findUserByCpf(String cpf);

    User findUserByEmail(String email);

    User findUserByRegistration(String registration);
}
