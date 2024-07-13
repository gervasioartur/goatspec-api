package com.goatspec.application.useCases.contracts.authentication;

import com.goatspec.domain.entities.user.UserAccount;

public interface ISinginUseCase {
    UserAccount singin(String cpf, String password);
}

