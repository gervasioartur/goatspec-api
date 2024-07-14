package com.goatspec.infrastructure.gateways.mappers;

import com.goatspec.domain.Enums.SpecializationTypeEnum;
import com.goatspec.domain.entities.specialization.Specialization;
import com.goatspec.infrastructure.api.dto.CreateSpecializationRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
public class SpecializationDTOMapperTests {

    @Test
    @DisplayName("Should return Specialization domain object")
    void shouldReturnSpecializationDomainObject() {
        UUID userid = UUID.randomUUID();
        CreateSpecializationRequest request = new CreateSpecializationRequest("any_area", SpecializationTypeEnum.DOCTORATE_DEGREE.getValue(), 60, new BigDecimal("25"));

        SpecializationDTOMapper specializationDTOMapper = new SpecializationDTOMapper();
        Specialization result=  specializationDTOMapper.toDomainObject(request,userid);

        Assertions.assertThat(result.userId()).isEqualTo(userid);
        Assertions.assertThat(result.area()).isEqualTo(request.area());
        Assertions.assertThat(result.type()).isEqualTo(request.type());
        Assertions.assertThat(result.courseLoad()).isEqualTo(request.courseLoad());
        Assertions.assertThat(result.totalCost()).isEqualTo(request.totalCost());
    }
}
