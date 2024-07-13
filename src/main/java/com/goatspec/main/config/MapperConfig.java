package com.goatspec.main.config;

import com.goatspec.infrastructure.gateways.mappers.RoleEntityMapper;
import com.goatspec.infrastructure.gateways.mappers.UserDTOMapper;
import com.goatspec.infrastructure.gateways.mappers.UserEntityMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public UserEntityMapper userEntityMapper() {
        return new UserEntityMapper();
    }

    @Bean
    public RoleEntityMapper roleEntityMapper() {
        return new RoleEntityMapper();
    }

    @Bean
    public UserDTOMapper userDTOMapper() {
        return new UserDTOMapper();
    }
}
