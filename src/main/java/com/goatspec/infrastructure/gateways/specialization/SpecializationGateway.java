package com.goatspec.infrastructure.gateways.specialization;

import com.goatspec.application.gateways.specialization.ISpecializationGateway;
import com.goatspec.domain.Enums.SpeciaiizationStatusEnum;
import com.goatspec.domain.entities.specialization.Specialization;
import com.goatspec.domain.entities.specialization.SpecializationAndUser;
import com.goatspec.domain.exceptions.NotFoundException;
import com.goatspec.infrastructure.gateways.mappers.SpecializationEntityMapper;
import com.goatspec.infrastructure.persisntence.entities.SpecializationEntity;
import com.goatspec.infrastructure.persisntence.entities.SpecializationStatusEntity;
import com.goatspec.infrastructure.persisntence.entities.UserEntity;
import com.goatspec.infrastructure.persisntence.repositories.ISpecializationRepository;
import com.goatspec.infrastructure.persisntence.repositories.ISpecializationStatusRepository;
import com.goatspec.infrastructure.persisntence.repositories.IUserRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
public class SpecializationGateway implements ISpecializationGateway {
    private final ISpecializationStatusRepository specializationStatusRepository;
    private final ISpecializationRepository specializationRepository;
    private final SpecializationEntityMapper specializationEntityMapper;
    private final IUserRepository userRepository;


    public SpecializationGateway(ISpecializationStatusRepository specializationSituationRepository,
                                 ISpecializationRepository specializationRepository,
                                 SpecializationEntityMapper specializationEntityMapper,
                                 IUserRepository userRepository) {
        this.specializationStatusRepository = specializationSituationRepository;
        this.specializationRepository = specializationRepository;
        this.specializationEntityMapper = specializationEntityMapper;
        this.userRepository = userRepository;
    }

    @Override
    public Specialization create(Specialization specialization) {
        SpecializationEntity specializationEntity = this.specializationEntityMapper.toSpecializationEntity(specialization);

        SpecializationStatusEntity specializationStatusEntity = this.specializationStatusRepository
                .findByDescriptionAndActive(SpeciaiizationStatusEnum.PENDING.getValue(), true);
        Optional<UserEntity> userEntity = this.userRepository.findByIdAndActive(specialization.userId(), true);

        specializationEntity.setActive(true);
        specializationEntity.setUser(userEntity.get());
        specializationEntity.setSpecializationStatus(specializationStatusEntity);
        specializationEntity = this.specializationRepository.save(specializationEntity);
        return this.specializationEntityMapper.toSpecializationDomainObject(specializationEntity);
    }

    @Override
    public List<SpecializationAndUser> getAll() {
        return this.specializationEntityMapper.toSpecAndUserListDomainObjects(this.specializationRepository.findAll());
    }

    @Override
    public Specialization findById(UUID id) {
        Optional<SpecializationEntity> specializationEntityResult = this.specializationRepository.findByIdAndActive(id, true);
        return specializationEntityResult.map(this.specializationEntityMapper::toSpecializationDomainObject).orElse(null);
    }

    @Override
    public SpecializationAndUser approve(UUID id) {
        SpecializationEntity specializationEntity = this.specializationRepository
                .findByIdAndActive(id, true)
                .orElseThrow(() -> new NotFoundException("Specialization not found."));

        SpecializationStatusEntity status = this.specializationStatusRepository
                .findByDescriptionAndActive(SpeciaiizationStatusEnum.APPROVED.getValue(), true);

        specializationEntity.setSpecializationStatus(status);
        specializationEntity = this.specializationRepository.save(specializationEntity);
        return this.specializationEntityMapper.toSpecAndUserDomainObject(specializationEntity);
    }
}
