package com.goatspec.main.config;

import com.goatspec.infrastructure.gateways.mappers.*;
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

    @Bean
    public SpecializationEntityMapper specializationEntityMapper(UserEntityMapper userEntityMapper) {
        return new SpecializationEntityMapper(userEntityMapper);
    }

    @Bean
    public SpecializationDTOMapper specializationDTOMapper() {
        return new SpecializationDTOMapper();
    }
}
