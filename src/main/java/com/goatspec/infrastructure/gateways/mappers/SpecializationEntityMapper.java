package com.goatspec.infrastructure.gateways.mappers;

import com.goatspec.domain.entities.authentication.UserInfo;
import com.goatspec.domain.entities.specialization.SpecializationRequest;
import com.goatspec.domain.entities.specialization.SpecializationRequestAndUser;
import com.goatspec.domain.entities.specialization.SpecializationRequestInfo;
import com.goatspec.infrastructure.persisntence.entities.SpecializationRequestEntity;
import com.goatspec.infrastructure.persisntence.entities.UserEntity;

import java.util.List;
import java.util.stream.Collectors;


public class SpecializationEntityMapper {
    private final UserEntityMapper userEntityMapper;

    public SpecializationEntityMapper(UserEntityMapper userEntityMapper) {
        this.userEntityMapper = userEntityMapper;
    }

    public SpecializationRequestEntity toSpecializationEntity(SpecializationRequest specializationRequestDomainObject) {
        return SpecializationRequestEntity
                .builder()
                .user(UserEntity.builder().id(specializationRequestDomainObject.userId()).build())
                .area(specializationRequestDomainObject.area())
                .type(specializationRequestDomainObject.type())
                .courseLoad(specializationRequestDomainObject.courseLoad())
                .totalCost(specializationRequestDomainObject.totalCost())
                .build();
    }

    public SpecializationRequest toSpecializationDomainObject(SpecializationRequestEntity specializationRequestEntity) {
        return new SpecializationRequest(specializationRequestEntity.getUser().getId(), specializationRequestEntity.getArea(), specializationRequestEntity.getType(), specializationRequestEntity.getCourseLoad(), specializationRequestEntity.getTotalCost());
    }

    public List<SpecializationRequestInfo> toSpecializationInfoList(List<SpecializationRequestEntity> specializationEntities) {
        return specializationEntities.stream()
                .map(specializationEntity -> new SpecializationRequestInfo(
                        specializationEntity.getUser().getName(),
                        specializationEntity.getUser().getEmail(),
                        specializationEntity.getUser().getRegistration(),
                        specializationEntity.getId().toString(),
                        specializationEntity.getArea(),
                        specializationEntity.getType(),
                        specializationEntity.getCourseLoad(),
                        specializationEntity.getTotalCost(),
                        specializationEntity.getSpecializationRequestStatus().getDescription()))
                .collect(Collectors.toList());
    }

    public SpecializationRequestAndUser toSpecAndUserDomainObject(SpecializationRequestEntity specializationRequestEntity) {
        return new SpecializationRequestAndUser(
                new UserInfo(specializationRequestEntity.getUser().getId(),
                        specializationRequestEntity.getUser().getName(),
                        specializationRequestEntity.getUser().getEmail(),
                        specializationRequestEntity.getUser().getRegistration()),
                this.toSpecializationDomainObject(specializationRequestEntity),
                specializationRequestEntity.getSpecializationRequestStatus().getDescription()
        );
    }
}
