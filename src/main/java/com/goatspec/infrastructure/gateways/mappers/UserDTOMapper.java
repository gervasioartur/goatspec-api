package com.goatspec.infrastructure.gateways.mappers;

import com.goatspec.domain.entities.user.User;
import com.goatspec.infrastructure.api.dto.CreateUserRequest;

public class UserDTOMapper {
    public User toDomainObject(CreateUserRequest request) {
        return new User(request.cpf(), request.email(), request.registration(), request.name(), request.dateOfBirth(), request.gender(), request.role(), request.password());
    }
}
