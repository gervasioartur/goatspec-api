package com.goatspec.main.config;

import com.goatspec.application.gateways.email.ISendEmailGateway;
import com.goatspec.application.gateways.specialization.ISpecializationRequestGateway;
import com.goatspec.application.gateways.user.IUserGateway;
import com.goatspec.application.useCases.contracts.specialization.IApproveSpecializationRequestUseCase;
import com.goatspec.application.useCases.contracts.specialization.IListAllSpecializationRequestsUseCase;
import com.goatspec.application.useCases.implementations.specialization.ApproveSpecializationRequestUseCase;
import com.goatspec.application.useCases.implementations.specialization.CreateSpecializationRequestRequestUseCase;
import com.goatspec.application.useCases.implementations.specialization.ListAllSpecializationRequestsUseCase;
import com.goatspec.infrastructure.gateways.mappers.SpecializationEntityMapper;
import com.goatspec.infrastructure.gateways.specialization.SpecializationRequestGateway;
import com.goatspec.infrastructure.persisntence.repositories.ISpecializationRequestRepository;
import com.goatspec.infrastructure.persisntence.repositories.ISpecializationRequestStatusRepository;
import com.goatspec.infrastructure.persisntence.repositories.IUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpecializationConfig {
    @Bean
    public CreateSpecializationRequestRequestUseCase createSpecializationUseCase(IUserGateway userGateway, ISpecializationRequestGateway specializationGateway) {
        return new CreateSpecializationRequestRequestUseCase(userGateway, specializationGateway);
    }

    @Bean
    public IListAllSpecializationRequestsUseCase specializationRequestsUseCase(ISpecializationRequestGateway specializationGateway) {
        return new ListAllSpecializationRequestsUseCase(specializationGateway);
    }

    @Bean
    public IApproveSpecializationRequestUseCase approveSpecializationRequestUseCase(
            ISpecializationRequestGateway specializationGateway, ISendEmailGateway sendEmailGateway) {
        return new ApproveSpecializationRequestUseCase(specializationGateway, sendEmailGateway);
    }

    @Bean
    public SpecializationRequestGateway specializationGateway(ISpecializationRequestStatusRepository specializationSituationRepository,
                                                              ISpecializationRequestRepository specializationRepository,
                                                              SpecializationEntityMapper specializationEntityMapper,
                                                              IUserRepository userRepository) {
        return new SpecializationRequestGateway(specializationSituationRepository,
                specializationRepository,
                specializationEntityMapper,
                userRepository);
    }

}
