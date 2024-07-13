package com.goatspec.infrastructure.gateways.mappers;

import com.goatspec.domain.entities.role.Role;
import com.goatspec.infrastructure.persisntence.entities.RoleEntity;

public class RoleEntityMapper {
    public RoleEntity toRoleEntity(Role roleDomainObject) {
        return RoleEntity.builder().name(roleDomainObject.name()).build();
    }

    public Role toDomainObject(RoleEntity roleEntity) {
        return new Role(roleEntity.getName());
    }
}
