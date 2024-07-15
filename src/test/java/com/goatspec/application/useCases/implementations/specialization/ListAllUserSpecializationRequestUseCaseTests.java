package com.goatspec.application.useCases.implementations.specialization;

import com.goatspec.application.gateways.specialization.ISpecializationRequestGateway;
import com.goatspec.domain.entities.specialization.SpecializationRequestInfo;
import com.goatspec.mocks.Mocks;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.UUID;

@SpringBootTest
public class ListAllUserSpecializationRequestUseCaseTests {
    private  ListAllUserSpecializationRequestUseCase listAllUserSpecializationRequestUseCase;

    @MockBean
    private ISpecializationRequestGateway specializationRequestGateway;

    @BeforeEach
    void setUp() {
        this.listAllUserSpecializationRequestUseCase = new ListAllUserSpecializationRequestUseCase(specializationRequestGateway);
    }

    @Test
    @DisplayName("Should return all user specialization request")
    void testListAllUserSpecializationRequest() {
        UUID userId = UUID.randomUUID();

        List<SpecializationRequestInfo> list = List
                .of(Mocks.specializationRequestInfoFactory(),Mocks.specializationRequestInfoFactory());

        Mockito.when(this.specializationRequestGateway.getAllByUserId(userId)).thenReturn(list);

        List<SpecializationRequestInfo> result = this.listAllUserSpecializationRequestUseCase.list(userId);

        Assertions.assertThat(result).hasSize(list.size());
        Assertions.assertThat(result).isEqualTo(list);
        Assertions.assertThat(result.getFirst().specializationRequestId()).isEqualTo(list.getFirst().specializationRequestId());
        Assertions.assertThat(result.getLast().specializationRequestId()).isEqualTo(list.getFirst().specializationRequestId());
    }
}
