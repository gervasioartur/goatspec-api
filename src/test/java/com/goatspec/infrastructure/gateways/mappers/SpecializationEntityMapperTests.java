package com.goatspec.infrastructure.gateways.mappers;

import com.goatspec.domain.entities.specialization.Specialization;
import com.goatspec.infrastructure.persisntence.entities.SpecializationEntity;
import com.goatspec.infrastructure.persisntence.entities.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
class SpecializationEntityMapperTests {
    private SpecializationEntityMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper =  new SpecializationEntityMapper(new UserEntityMapper());
    }


    @Test
    @DisplayName("Should return specialization entity")
    void shouldReturnSpecializationEntity() {
        Specialization specializationDomainObject = new Specialization
                (UUID.randomUUID(), "any_area", "any_type", 2, new BigDecimal("25"));

        SpecializationEntity specializationEntity = mapper.toSpecializationEntity(specializationDomainObject);

        Assertions.assertThat(specializationEntity.getUser().getId()).isEqualTo(specializationDomainObject.userId());
        Assertions.assertThat(specializationEntity.getArea()).isEqualTo(specializationDomainObject.area());
        Assertions.assertThat(specializationEntity.getType()).isEqualTo(specializationDomainObject.type());
        Assertions.assertThat(specializationEntity.getCourseLoad()).isEqualTo(specializationDomainObject.courseLoad());
        Assertions.assertThat(specializationEntity.getTotalCost()).isEqualTo(specializationDomainObject.totalCost());
    }

    @Test
    @DisplayName("Shpuld return specialization domanin object")
    void shouldReturnSpecializationDomainObject() {
        SpecializationEntity specializationEntity = SpecializationEntity
                .builder()
                .user(UserEntity.builder().id(UUID.randomUUID()).build())
                .area("any_area")
                .type("any_type")
                .courseLoad(2)
                .totalCost(new BigDecimal(20))
                .build();


        Specialization specializationDomainObject = mapper.toDomainObject(specializationEntity);

        Assertions.assertThat(specializationEntity.getUser().getId()).isEqualTo(specializationDomainObject.userId());
        Assertions.assertThat(specializationEntity.getArea()).isEqualTo(specializationDomainObject.area());
        Assertions.assertThat(specializationEntity.getType()).isEqualTo(specializationDomainObject.type());
        Assertions.assertThat(specializationEntity.getCourseLoad()).isEqualTo(specializationDomainObject.courseLoad());
        Assertions.assertThat(specializationEntity.getTotalCost()).isEqualTo(specializationDomainObject.totalCost());
    }
}
