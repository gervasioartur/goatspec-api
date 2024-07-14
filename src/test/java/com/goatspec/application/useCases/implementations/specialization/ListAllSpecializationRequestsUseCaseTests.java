package com.goatspec.application.useCases.implementations.specialization;

import com.goatspec.application.gateways.specialization.ISpecializationGateway;
import com.goatspec.application.useCases.contracts.specialization.IListAllSpecializationRequestsUseCase;
import com.goatspec.domain.entities.authentication.UserInfo;
import com.goatspec.domain.entities.specialization.Specialization;
import com.goatspec.domain.entities.specialization.SpecializationAndUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class ListAllSpecializationRequestsUseCaseTests {
    private IListAllSpecializationRequestsUseCase listAllSpecializationRequests;
    @MockBean
    private ISpecializationGateway specializationGateway;

    @BeforeEach
    void setUp() {
        this.listAllSpecializationRequests = new ListAllSpecializationRequestsUseCase(specializationGateway);
    }


    @Test
    @DisplayName("Should return all specialization")
    void shouldReturnAllSpecialization() {
        UUID userId = UUID.randomUUID();
        UUID userId1 = UUID.randomUUID();

        SpecializationAndUser specializationAndUser = new SpecializationAndUser(
                new UserInfo(userId, "any_name", "any_email", "any_registration"),
                new Specialization(userId, "any_area", "any_type", 36, new BigDecimal("200")),
                "PENDING"
        );

        SpecializationAndUser specializationAndUser1 = new SpecializationAndUser(
                new UserInfo(userId1, "any_name", "any_email", "any_registration"),
                new Specialization(userId1, "any_area", "any_type", 36, new BigDecimal("200")),
                "PENDING"
        );

        List<SpecializationAndUser> list = List.of(specializationAndUser, specializationAndUser1);
        Mockito.when(this.specializationGateway.getAll()).thenReturn(list);

        List<SpecializationAndUser> listResult = this.listAllSpecializationRequests.getAll();

        Assertions.assertThat(listResult).isEqualTo(list);
        Assertions.assertThat(listResult.size()).isEqualTo(list.size());
        Assertions.assertThat(listResult.get(0)).isEqualTo(specializationAndUser);
        Mockito.verify(this.specializationGateway, Mockito.times(1)).getAll();
    }


}
