package com.goatspec.main.config;

import com.goatspec.application.gateways.specialization.ISpecializationGateway;
import com.goatspec.application.gateways.user.IUserGateway;
import com.goatspec.application.useCases.implementations.specialization.CreateSpecializationUseCase;
import com.goatspec.infrastructure.gateways.mappers.SpecializationEntityMapper;
import com.goatspec.infrastructure.gateways.specialization.SpecializationGateway;
import com.goatspec.infrastructure.persisntence.repositories.ISpecializationRepository;
import com.goatspec.infrastructure.persisntence.repositories.ISpecializationStatusRepository;
import com.goatspec.infrastructure.persisntence.repositories.IUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpecializationConfig {
    @Bean
    public CreateSpecializationUseCase createSpecializationUseCase(IUserGateway userGateway, ISpecializationGateway specializationGateway) {
        return new CreateSpecializationUseCase(userGateway, specializationGateway);
    }

    @Bean
    public SpecializationGateway specializationGateway(ISpecializationStatusRepository specializationSituationRepository, ISpecializationRepository specializationRepository, SpecializationEntityMapper specializationEntityMapper, IUserRepository userRepository) {
        return new SpecializationGateway(specializationSituationRepository, specializationRepository, specializationEntityMapper, userRepository);
    }
}
