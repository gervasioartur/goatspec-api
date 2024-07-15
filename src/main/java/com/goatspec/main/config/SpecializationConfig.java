package com.goatspec.main.config;

import com.goatspec.application.gateways.email.ISendEmailGateway;
import com.goatspec.application.gateways.specialization.ISpecializationRequestGateway;
import com.goatspec.application.gateways.user.IUserGateway;
import com.goatspec.application.useCases.contracts.specialization.*;
import com.goatspec.application.useCases.implementations.specialization.*;
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
    public IListAllSpecializationRequestsUseCase listAllSpecializationRequestsUseCase(ISpecializationRequestGateway specializationGateway) {
        return new ListAllSpecializationRequestsUseCase(specializationGateway);
    }

    @Bean
    public IListAllUserSpecializationRequestUseCase listAllUserSpecializationRequestUseCase(ISpecializationRequestGateway specializationGateway) {
        return new ListAllUserSpecializationRequestUseCase(specializationGateway);
    }

    @Bean
    public IApproveSpecializationRequestUseCase approveSpecializationRequestUseCase(
            ISpecializationRequestGateway specializationGateway, ISendEmailGateway sendEmailGateway) {
        return new ApproveSpecializationRequestUseCase(specializationGateway, sendEmailGateway);
    }

    @Bean
    public IDisapproveSpecializationRequestUseCase disapproveSpecializationRequestUseCase(
            ISpecializationRequestGateway specializationGateway, ISendEmailGateway sendEmailGateway) {

        return new DisapproveSpecializationRequestUseCase(specializationGateway, sendEmailGateway);
    }

    @Bean
    public IRemoveSpecializationRequestUseCase removeSpecializationRequestUseCase(ISpecializationRequestGateway specializationRequestGateway) {
        return new RemoveSpecializationRequestUseCase(specializationRequestGateway);
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
