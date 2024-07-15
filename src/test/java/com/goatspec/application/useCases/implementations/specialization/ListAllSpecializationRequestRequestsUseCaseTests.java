package com.goatspec.application.useCases.implementations.specialization;

import com.goatspec.application.gateways.specialization.ISpecializationRequestGateway;
import com.goatspec.application.useCases.contracts.specialization.IListAllSpecializationRequestsUseCase;
import com.goatspec.domain.entities.authentication.UserInfo;
import com.goatspec.domain.entities.specialization.SpecializationRequest;
import com.goatspec.domain.entities.specialization.SpecializationRequestAndUser;
import com.goatspec.domain.entities.specialization.SpecializationRequestInfo;
import com.goatspec.mocks.Mocks;
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
class ListAllSpecializationRequestRequestsUseCaseTests {
    private IListAllSpecializationRequestsUseCase listAllSpecializationRequests;
    @MockBean
    private ISpecializationRequestGateway specializationGateway;

    @BeforeEach
    void setUp() {
        this.listAllSpecializationRequests = new ListAllSpecializationRequestsUseCase(specializationGateway);
    }


    @Test
    @DisplayName("Should return all specialization")
    void shouldReturnAllSpecialization() {
        List<SpecializationRequestInfo> list = List.of(Mocks.SpecializationRequestInfoFactory(),Mocks.SpecializationRequestInfoFactory());
        Mockito.when(this.specializationGateway.getAll()).thenReturn(list);

        List<SpecializationRequestInfo> listResult = this.listAllSpecializationRequests.getAll();

        Assertions.assertThat(listResult).isEqualTo(list);
        Assertions.assertThat(listResult.size()).isEqualTo(list.size());
        Assertions.assertThat(listResult.getFirst()).isEqualTo(list.getFirst());
        Mockito.verify(this.specializationGateway, Mockito.times(1)).getAll();
    }


}
