package com.goatspec.application.gateways.role;


import com.goatspec.domain.entities.role.Role;

public interface IRoleGateway {
    Role findRoleByName(String name);
}
