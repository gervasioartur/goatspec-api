package com.goatspec.gateways;

import com.goatspec.domain.entities.User;

public interface IUserGateway {
    User findUserByCpf(String cpf);
    User findUserByEmail(String email);
}
