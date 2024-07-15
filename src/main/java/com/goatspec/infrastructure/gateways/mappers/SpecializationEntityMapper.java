package com.goatspec.infrastructure.gateways.mappers;

import com.goatspec.domain.entities.authentication.UserInfo;
import com.goatspec.domain.entities.specialization.Specialization;
import com.goatspec.domain.entities.specialization.SpecializationAndUser;
import com.goatspec.infrastructure.persisntence.entities.SpecializationEntity;
import com.goatspec.infrastructure.persisntence.entities.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

public class SpecializationEntityMapper {
    private final UserEntityMapper userEntityMapper;

    public SpecializationEntityMapper(UserEntityMapper userEntityMapper) {
        this.userEntityMapper = userEntityMapper;
    }

    public SpecializationEntity toSpecializationEntity(Specialization specializationDomainObject) {
        return SpecializationEntity
                .builder()
                .user(UserEntity.builder().id(specializationDomainObject.userId()).build())
                .area(specializationDomainObject.area())
                .type(specializationDomainObject.type())
                .courseLoad(specializationDomainObject.courseLoad())
                .totalCost(specializationDomainObject.totalCost())
                .build();
    }

    public Specialization toSpecializationDomainObject(SpecializationEntity specializationEntity) {
        return new Specialization(specializationEntity.getUser().getId(), specializationEntity.getArea(), specializationEntity.getType(), specializationEntity.getCourseLoad(), specializationEntity.getTotalCost());
    }

    public List<SpecializationAndUser> toSpecAndUserListDomainObjects(List<SpecializationEntity> specializationEntities) {
        return specializationEntities.stream()
                .map(specializationEntity -> {
                    UserInfo userInfo = this.userEntityMapper.toUserInfo(specializationEntity.getUser());
                    Specialization specialization = this.toSpecializationDomainObject(specializationEntity);
                    return new SpecializationAndUser(userInfo, specialization, specializationEntity.getSpecializationStatus().getDescription());
                }).collect(Collectors.toList());
    }

    public SpecializationAndUser toSpecAndUserDomainObject(SpecializationEntity specializationEntity) {
        return new SpecializationAndUser(
                new UserInfo(specializationEntity.getUser().getId(),
                        specializationEntity.getUser().getName(),
                        specializationEntity.getUser().getEmail(),
                        specializationEntity.getUser().getRegistration()),
                this.toSpecializationDomainObject(specializationEntity),
                specializationEntity.getSpecializationStatus().getDescription()
        );
    }
}
