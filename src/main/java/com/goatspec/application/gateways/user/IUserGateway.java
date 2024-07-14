package com.goatspec.application.gateways.user;

import com.goatspec.domain.entities.user.User;

import java.util.UUID;

public interface IUserGateway {
    User create(User user);

    User findUserById(UUID id);

    User findUserByCpf(String cpf);

    User findUserByEmail(String email);

    User findUserByRegistration(String registration);
}
