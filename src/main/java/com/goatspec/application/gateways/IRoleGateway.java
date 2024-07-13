package com.goatspec.application.gateways;


import com.goatspec.domain.entities.Role;

public interface IRoleGateway {
    Role findRoleByName(String name);
}
