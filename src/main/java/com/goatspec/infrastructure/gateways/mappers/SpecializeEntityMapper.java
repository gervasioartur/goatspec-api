package com.goatspec.infrastructure.gateways.mappers;

import com.goatspec.domain.entities.specialization.Specialization;
import com.goatspec.infrastructure.persisntence.entities.SpecializationEntity;
import com.goatspec.infrastructure.persisntence.entities.UserEntity;

public class SpecializeEntityMapper {
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
}
