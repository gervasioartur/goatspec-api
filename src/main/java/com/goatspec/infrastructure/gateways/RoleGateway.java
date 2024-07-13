package com.goatspec.infrastructure.gateways;

import com.goatspec.application.gateways.IRoleGateway;
import com.goatspec.domain.entities.Role;
import com.goatspec.infrastructure.persisntence.entoties.RoleEntity;
import com.goatspec.infrastructure.persisntence.repositories.IRoleRepository;

public class RoleGateway implements IRoleGateway {
    private IRoleRepository roleRepository;

    public RoleGateway(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findRoleByName(String name) {
       RoleEntity roleEntity = this.roleRepository.findByNameAndActive(name,true);
        if (roleEntity == null) return null;
        return null;
    }
}
