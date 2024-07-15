package com.goatspec.application.useCases.contracts.authentication;

import com.goatspec.domain.entities.user.User;
import com.goatspec.domain.entities.user.UserAccount;

public interface ISingupUseCase {
    UserAccount create(User userDomainObject);
}
