package com.goatspec.main.config;

import com.goatspec.application.gateways.role.IRoleGateway;
import com.goatspec.infrastructure.gateways.mappers.RoleEntityMapper;
import com.goatspec.infrastructure.gateways.role.RoleGateway;
import com.goatspec.infrastructure.persisntence.repositories.IRoleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleConfig {

    @Bean
    public IRoleGateway roleGateway(IRoleRepository roleRepository, RoleEntityMapper roleEntityMapper){
        return new RoleGateway(roleRepository, roleEntityMapper);
    }
}
