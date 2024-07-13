package com.goatspec.application.useCases.contracts.user;

import com.goatspec.domain.entities.user.User;
import com.goatspec.domain.entities.user.UserAccount;

public interface ICreateUserUseCase {
    UserAccount create(User userDomainObject);
}
