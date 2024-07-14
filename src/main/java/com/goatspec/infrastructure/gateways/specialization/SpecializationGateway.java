package com.goatspec.infrastructure.gateways.specialization;

import com.goatspec.application.gateways.specialization.ISpecializationGateway;
import com.goatspec.domain.Enums.SpeciaiizationSituationEnum;
import com.goatspec.domain.entities.specialization.Specialization;
import com.goatspec.infrastructure.gateways.mappers.SpecializationEntityMapper;
import com.goatspec.infrastructure.persisntence.entities.SpecializationEntity;
import com.goatspec.infrastructure.persisntence.entities.SpecializationStatusEntity;
import com.goatspec.infrastructure.persisntence.entities.UserEntity;
import com.goatspec.infrastructure.persisntence.repositories.ISpecializationRepository;
import com.goatspec.infrastructure.persisntence.repositories.ISpecializationStatusRepository;
import com.goatspec.infrastructure.persisntence.repositories.IUserRepository;

import java.util.Optional;

public class SpecializationGateway implements ISpecializationGateway {
    private final ISpecializationStatusRepository specializationSituationRepository;
    private final ISpecializationRepository specializationRepository;
    private final SpecializationEntityMapper specializationEntityMapper;
    private final IUserRepository userRepository;


    public SpecializationGateway(ISpecializationStatusRepository specializationSituationRepository, ISpecializationRepository specializationRepository, SpecializationEntityMapper specializationEntityMapper, IUserRepository userRepository) {
        this.specializationSituationRepository = specializationSituationRepository;
        this.specializationRepository = specializationRepository;
        this.specializationEntityMapper = specializationEntityMapper;
        this.userRepository = userRepository;
    }

    @Override
    public Specialization create(Specialization specialization) {
        SpecializationEntity specializationEntity = this.specializationEntityMapper.toSpecializationEntity(specialization);

        SpecializationStatusEntity specializationStatusEntity = this.specializationSituationRepository
                .findByDescriptionAndActive(SpeciaiizationSituationEnum.PENDING.getValue(), true);
        Optional<UserEntity> userEntity = this.userRepository.findByIdAndActive(specialization.userId(), true);

        specializationEntity.setActive(true);
        specializationEntity.setUser(userEntity.get());
        specializationEntity.setSpecializationStatus(specializationStatusEntity);
        specializationEntity = this.specializationRepository.save(specializationEntity);
        return this.specializationEntityMapper.toDomainObject(specializationEntity);
    }
}
