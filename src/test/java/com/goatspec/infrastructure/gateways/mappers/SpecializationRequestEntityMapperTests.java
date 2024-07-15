package com.goatspec.infrastructure.gateways.mappers;

import com.goatspec.domain.entities.specialization.SpecializationRequest;
import com.goatspec.infrastructure.persisntence.entities.SpecializationRequestEntity;
import com.goatspec.infrastructure.persisntence.entities.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
class SpecializationRequestEntityMapperTests {
    private SpecializationEntityMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new SpecializationEntityMapper(new UserEntityMapper());
    }


    @Test
    @DisplayName("Should return specialization entity")
    void shouldReturnSpecializationEntity() {
        SpecializationRequest specializationRequestDomainObject = new SpecializationRequest
                (UUID.randomUUID(), "any_area", "any_type", 2, new BigDecimal("25"));

        SpecializationRequestEntity specializationRequestEntity = mapper.toSpecializationEntity(specializationRequestDomainObject);

        Assertions.assertThat(specializationRequestEntity.getUser().getId()).isEqualTo(specializationRequestDomainObject.userId());
        Assertions.assertThat(specializationRequestEntity.getArea()).isEqualTo(specializationRequestDomainObject.area());
        Assertions.assertThat(specializationRequestEntity.getType()).isEqualTo(specializationRequestDomainObject.type());
        Assertions.assertThat(specializationRequestEntity.getCourseLoad()).isEqualTo(specializationRequestDomainObject.courseLoad());
        Assertions.assertThat(specializationRequestEntity.getTotalCost()).isEqualTo(specializationRequestDomainObject.totalCost());
    }

    @Test
    @DisplayName("Shpuld return specialization domanin object")
    void shouldReturnSpecializationDomainObject() {
        SpecializationRequestEntity specializationRequestEntity = SpecializationRequestEntity
                .builder()
                .user(UserEntity.builder().id(UUID.randomUUID()).build())
                .area("any_area")
                .type("any_type")
                .courseLoad(2)
                .totalCost(new BigDecimal(20))
                .build();


        SpecializationRequest specializationRequestDomainObject = mapper.toSpecializationDomainObject(specializationRequestEntity);

        Assertions.assertThat(specializationRequestEntity.getUser().getId()).isEqualTo(specializationRequestDomainObject.userId());
        Assertions.assertThat(specializationRequestEntity.getArea()).isEqualTo(specializationRequestDomainObject.area());
        Assertions.assertThat(specializationRequestEntity.getType()).isEqualTo(specializationRequestDomainObject.type());
        Assertions.assertThat(specializationRequestEntity.getCourseLoad()).isEqualTo(specializationRequestDomainObject.courseLoad());
        Assertions.assertThat(specializationRequestEntity.getTotalCost()).isEqualTo(specializationRequestDomainObject.totalCost());
    }
}
