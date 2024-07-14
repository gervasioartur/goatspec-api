package com.goatspec.infrastructure.gateways.specialization;

import com.goatspec.application.gateways.specialization.ISpecializationGateway;
import com.goatspec.domain.Enums.SpeciaiizationSituationEnum;
import com.goatspec.domain.entities.specialization.Specialization;
import com.goatspec.domain.entities.specialization.SpecializationAndUser;
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

    @Override
    public List<SpecializationAndUser> getAll() {
        return this.specializationEntityMapper.toDomainObjects(this.specializationRepository.findAll());
    }

    @Override
    public Specialization findById(UUID id) {
       Optional<SpecializationEntity> specializationEntityResult = this.specializationRepository.findByIdAndActive(id,true);
        return specializationEntityResult.map(this.specializationEntityMapper::toDomainObject).orElse(null);
    }

    @Override
    public void approve(UUID id) {

    }

}
