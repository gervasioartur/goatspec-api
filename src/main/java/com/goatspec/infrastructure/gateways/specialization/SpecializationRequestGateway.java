package com.goatspec.infrastructure.gateways.specialization;

import com.goatspec.application.gateways.specialization.ISpecializationRequestGateway;
import com.goatspec.domain.Enums.SpecializationRequestStatusEnum;
import com.goatspec.domain.entities.specialization.SpecializationRequest;
import com.goatspec.domain.entities.specialization.SpecializationRequestInfo;
import com.goatspec.domain.exceptions.NotFoundException;
import com.goatspec.infrastructure.gateways.mappers.SpecializationEntityMapper;
import com.goatspec.infrastructure.persisntence.entities.SpecializationRequestEntity;
import com.goatspec.infrastructure.persisntence.entities.SpecializationRequestStatusEntity;
import com.goatspec.infrastructure.persisntence.entities.UserEntity;
import com.goatspec.infrastructure.persisntence.repositories.ISpecializationRequestRepository;
import com.goatspec.infrastructure.persisntence.repositories.ISpecializationRequestStatusRepository;
import com.goatspec.infrastructure.persisntence.repositories.IUserRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
public class SpecializationRequestGateway implements ISpecializationRequestGateway {
    private final ISpecializationRequestStatusRepository specializationStatusRepository;
    private final ISpecializationRequestRepository specializationRepository;
    private final SpecializationEntityMapper specializationEntityMapper;
    private final IUserRepository userRepository;


    public SpecializationRequestGateway(ISpecializationRequestStatusRepository specializationSituationRepository,
                                        ISpecializationRequestRepository specializationRepository,
                                        SpecializationEntityMapper specializationEntityMapper,
                                        IUserRepository userRepository) {
        this.specializationStatusRepository = specializationSituationRepository;
        this.specializationRepository = specializationRepository;
        this.specializationEntityMapper = specializationEntityMapper;
        this.userRepository = userRepository;
    }

    @Override
    public SpecializationRequest create(SpecializationRequest specializationRequest) {
        SpecializationRequestEntity specializationRequestEntity = this.specializationEntityMapper.toSpecializationEntity(specializationRequest);

        SpecializationRequestStatusEntity specializationRequestStatusEntity = this.specializationStatusRepository
                .findByDescriptionAndActive(SpecializationRequestStatusEnum.PENDING.getValue(), true);
        Optional<UserEntity> userEntity = this.userRepository.findByIdAndActive(specializationRequest.userId(), true);

        specializationRequestEntity.setActive(true);
        specializationRequestEntity.setUser(userEntity.get());
        specializationRequestEntity.setSpecializationRequestStatus(specializationRequestStatusEntity);
        specializationRequestEntity = this.specializationRepository.save(specializationRequestEntity);
        return this.specializationEntityMapper.toSpecializationDomainObject(specializationRequestEntity);
    }

    @Override
    public List<SpecializationRequestInfo> getAll() {
        return this.specializationEntityMapper.toSpecializationInfoList(this.specializationRepository.findAllByActive(true));
    }

    @Override
    public SpecializationRequestInfo findById(UUID id) {
        Optional<SpecializationRequestEntity> specializationEntityResult = this.specializationRepository.findByIdAndActive(id, true);
        return specializationEntityResult.map(this.specializationEntityMapper::toSpecializationInfo).orElse(null);
    }

    @Override
    public SpecializationRequestInfo findByIdAndUserId(UUID id, UUID userId) {
        Optional<SpecializationRequestEntity> specializationEntityResult = this.specializationRepository.findByIdAndActive(id, true);
        if (specializationEntityResult.isEmpty()) throw new NotFoundException("Specialization request not found.");
        SpecializationRequestEntity entity = specializationEntityResult.get();
        if (!entity.getUser().getId().equals(userId)) throw new NotFoundException("Specialization request not found.");
        return this.specializationEntityMapper.toSpecializationInfo(entity);
    }

    @Override
    public SpecializationRequestInfo approve(UUID id) {
        SpecializationRequestEntity specializationRequestEntity = this.specializationRepository
                .findByIdAndActive(id, true)
                .orElseThrow(() -> new NotFoundException("Specialization request not found."));

        SpecializationRequestStatusEntity status = this.specializationStatusRepository
                .findByDescriptionAndActive(SpecializationRequestStatusEnum.APPROVED.getValue(), true);

        specializationRequestEntity.setSpecializationRequestStatus(status);
        specializationRequestEntity = this.specializationRepository.save(specializationRequestEntity);
        return this.specializationEntityMapper.toSpecializationInfo(specializationRequestEntity);
    }

    @Override
    public void remove(UUID id) {
        SpecializationRequestEntity specializationRequestEntity = this.specializationRepository
                .findByIdAndActive(id, true)
                .orElseThrow(() -> new NotFoundException("Specialization request not found."));

        specializationRequestEntity.setActive(false);
        this.specializationRepository.save(specializationRequestEntity);
    }

    @Override
    public SpecializationRequestInfo disapprove(UUID id) {
        SpecializationRequestEntity specializationRequestEntity = this.specializationRepository
                .findByIdAndActive(id, true)
                .orElseThrow(() -> new NotFoundException("Specialization request not found."));

        SpecializationRequestStatusEntity status = this.specializationStatusRepository
                .findByDescriptionAndActive(SpecializationRequestStatusEnum.DISAPPROVED.getValue(), true);

        specializationRequestEntity.setSpecializationRequestStatus(status);
        specializationRequestEntity = this.specializationRepository.save(specializationRequestEntity);
        return this.specializationEntityMapper.toSpecializationInfo(specializationRequestEntity);
    }
}
