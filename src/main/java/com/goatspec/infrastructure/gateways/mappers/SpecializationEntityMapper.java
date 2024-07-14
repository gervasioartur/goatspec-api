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

    public Specialization toDomainObject(SpecializationEntity specializationEntity) {
        return new Specialization(specializationEntity.getUser().getId(), specializationEntity.getArea(), specializationEntity.getType(), specializationEntity.getCourseLoad(), specializationEntity.getTotalCost());
    }

    public List<SpecializationAndUser> toDomainObjects(List<SpecializationEntity> specializationEntities) {
        return specializationEntities.stream()
                .map(specializationEntity -> {
                    UserInfo userInfo = this.userEntityMapper.toUserInfo(specializationEntity.getUser());
                    Specialization specialization = this.toDomainObject(specializationEntity);
                    return new SpecializationAndUser(userInfo, specialization);
                }).collect(Collectors.toList());
    }
}
