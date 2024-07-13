package com.goatspec.infrastructure.gateways;

import com.goatspec.application.gateways.IRoleGateway;
import com.goatspec.domain.entities.Role;
import com.goatspec.infrastructure.gateways.mappers.RoleEntityMapper;
import com.goatspec.infrastructure.persisntence.entoties.RoleEntity;
import com.goatspec.infrastructure.persisntence.repositories.IRoleRepository;

public class RoleGateway implements IRoleGateway {
    private final IRoleRepository roleRepository;
    private final RoleEntityMapper roleEntityMapper;

    public RoleGateway(IRoleRepository roleRepository, RoleEntityMapper roleEntityMapper) {
        this.roleRepository = roleRepository;
        this.roleEntityMapper = roleEntityMapper;
    }

    @Override
    public Role findRoleByName(String name) {
        RoleEntity roleEntity = this.roleRepository.findByNameAndActive(name, true);
        if (roleEntity != null) return this.roleEntityMapper.toDomainObject(roleEntity);
        return null;
    }
}
