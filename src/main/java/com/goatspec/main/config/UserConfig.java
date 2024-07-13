package com.goatspec.main.config;

import com.goatspec.application.gateways.authentication.IAuthenticationGateway;
import com.goatspec.application.gateways.encrypt.IPasswordEncoderGateway;
import com.goatspec.application.gateways.role.IRoleGateway;
import com.goatspec.application.gateways.user.IUserGateway;
import com.goatspec.application.useCases.contracts.user.ICreateUserUseCase;
import com.goatspec.application.useCases.implementations.user.CreateUserUseCase;
import com.goatspec.infrastructure.gateways.mappers.UserEntityMapper;
import com.goatspec.infrastructure.gateways.user.UserGateway;
import com.goatspec.infrastructure.persisntence.repositories.IRoleRepository;
import com.goatspec.infrastructure.persisntence.repositories.IUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {
    @Bean
    public ICreateUserUseCase createUserUseCase(IUserGateway userGateway, IRoleGateway roleGateway, IPasswordEncoderGateway passwordEncoderGateway, IAuthenticationGateway authentication) {
        return new CreateUserUseCase(userGateway, roleGateway, passwordEncoderGateway, authentication);
    }

    @Bean
    public IUserGateway userGateway(IUserRepository userRepository, UserEntityMapper userEntityMapper, IRoleRepository roleRepository) {
        return new UserGateway(userRepository, userEntityMapper, roleRepository);
    }

}
