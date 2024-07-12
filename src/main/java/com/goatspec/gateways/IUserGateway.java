package com.goatspec.gateways;

import com.goatspec.domain.entities.User;

public interface IUserGateway {
    User create(User user);
    User findUserByCpf(String cpf);
    User findUserByEmail(String email);
    User findUserByRegistration(String registration);
}
