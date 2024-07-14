package com.goatspec.application.useCases.contracts.authentication;

import com.goatspec.domain.entities.authentication.UserInfo;

public interface IGetLoggedUserInfoUseCase {
    UserInfo get();
}
