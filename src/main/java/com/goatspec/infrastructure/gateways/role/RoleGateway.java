package com.goatspec.infrastructure.gateways.role;

import com.goatspec.application.gateways.role.IRoleGateway;
import com.goatspec.domain.entities.role.Role;
import com.goatspec.infrastructure.gateways.mappers.RoleEntityMapper;
import com.goatspec.infrastructure.persisntence.entities.RoleEntity;
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
        return (roleEntity != null) ? this.roleEntityMapper.toDomainObject(roleEntity) : null;
    }
}
