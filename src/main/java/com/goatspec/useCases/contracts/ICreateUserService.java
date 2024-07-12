package com.goatspec.useCases.contracts;

import com.goatspec.domain.entities.User;
import com.goatspec.domain.entities.UserAccount;

public interface ICreateUserService {
    UserAccount create(User userDomainObject);
}
