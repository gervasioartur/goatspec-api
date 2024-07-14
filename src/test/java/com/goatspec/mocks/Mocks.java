package com.goatspec.mocks;

import com.goatspec.domain.entities.authentication.UserInfo;
import com.goatspec.domain.entities.specialization.Specialization;
import com.goatspec.domain.entities.specialization.SpecializationAndUser;
import com.goatspec.infrastructure.persisntence.entities.RoleEntity;
import com.goatspec.infrastructure.persisntence.entities.SpecializationEntity;
import com.goatspec.infrastructure.persisntence.entities.SpecializationStatusEntity;
import com.goatspec.infrastructure.persisntence.entities.UserEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

public class Mocks {
    public static SpecializationEntity specializationEntityFactory() {
        return SpecializationEntity
                .builder()
                .id(UUID.randomUUID())
                .user(userFactory())
                .area("any_")
                .type("any_")
                .courseLoad(200)
                .totalCost(new BigDecimal(30))
                .specializationStatus(specializationStatusEntityFactory())
                .active(true)
                .build();
    }

    public static UserEntity userFactory() {
        return UserEntity
                .builder()
                .id(UUID.randomUUID())
                .cpf("any_cpf")
                .email("any_email")
                .registration("any_registration")
                .name("any_name")
                .dateOfBirth(new Date())
                .gender("any_gender")
                .password("any_password")
                .roles(Collections.singletonList(roleFactory()))
                .active(true)
                .build();
    }

    public static RoleEntity roleFactory() {
        return RoleEntity.builder()
                .name("any_name")
                .active(true)
                .build();
    }

    public static SpecializationStatusEntity specializationStatusEntityFactory() {
        return SpecializationStatusEntity
                .builder()
                .id(UUID.randomUUID())
                .description("any_description")
                .active(true)
                .build();
    }

    public static SpecializationAndUser specializationAndUserFactory(SpecializationEntity specializationEntity) {
        return new SpecializationAndUser(
                new UserInfo(specializationEntity.getUser().getId()
                        , specializationEntity.getUser().getName(),
                        specializationEntity.getUser().getEmail()
                        , specializationEntity.getUser().getRegistration()),
                new Specialization(specializationEntity.getUser().getId(), specializationEntity.getArea(), specializationEntity.getType(), specializationEntity.getCourseLoad(), specializationEntity.getTotalCost()),
                specializationEntity.getSpecializationStatus().getDescription()
        );
    }

    public static  Specialization specializationDomainObjectFactory(){
        return new Specialization(UUID.randomUUID(),"any_area","any_type",200,new BigDecimal(20));
    }

    public static  Specialization specializationDomainObjectFactory(SpecializationEntity specializationEntity){
        return new Specialization
                (specializationEntity.getUser().getId(),specializationEntity.getArea(),specializationEntity.getType(),specializationEntity.getCourseLoad(),specializationEntity.getTotalCost());
    }
}
