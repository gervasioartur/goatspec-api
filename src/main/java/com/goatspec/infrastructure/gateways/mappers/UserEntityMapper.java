package com.goatspec.infrastructure.gateways.mappers;

import com.goatspec.domain.entities.User;
import com.goatspec.infrastructure.persisntence.entoties.RoleEntity;
import com.goatspec.infrastructure.persisntence.entoties.UserEntity;

public class UserEntityMapper {
    public UserEntity toUserEntity(User userDomainObject) {
        return UserEntity
                .builder()
                .cpf(userDomainObject.cpf())
                .email(userDomainObject.email())
                .registration(userDomainObject.registration())
                .name(userDomainObject.name())
                .dateOfBirth(userDomainObject.dateOfBirth())
                .gender(userDomainObject.gender())
                .password(userDomainObject.password())
                .build();
    }

    public User toUserDomainObject(UserEntity userEntity, RoleEntity roleEntity) {
        return new User(userEntity.getCpf(), userEntity.getEmail(), userEntity.getRegistration(), userEntity.getName(), userEntity.getDateOfBirth(), userEntity.getGender(), roleEntity.getName(), userEntity.getPassword());
    }
}
