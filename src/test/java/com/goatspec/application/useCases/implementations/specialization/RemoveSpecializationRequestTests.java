package com.goatspec.application.useCases.implementations.specialization;

import com.goatspec.application.gateways.specialization.ISpecializationRequestGateway;
import com.goatspec.application.useCases.contracts.specialization.IRemoveSpecializationRequest;
import com.goatspec.domain.exceptions.BusinessException;
import com.goatspec.domain.exceptions.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

@SpringBootTest
public class RemoveSpecializationRequestTests {
    private IRemoveSpecializationRequest removeSpecializationRequest;
    @MockBean
    private ISpecializationRequestGateway specializationRequestGateway;

    @BeforeEach
    void setUp() {
        this.removeSpecializationRequest = new RemoveSpecializationRequest(specializationRequestGateway);
    }

    @Test
    @DisplayName("Should throw business exception if specialization request does not exist")
    void shouldThrowBusinessExceptionIfSpecializationRequestDoesNotExist() {
        UUID specializationId = UUID.randomUUID();

        Mockito.when(this.specializationRequestGateway.findById(specializationId)).thenReturn(null);

        Throwable exception = Assertions.catchThrowable(() -> this.removeSpecializationRequest.remove(specializationId));

        Assertions.assertThat(exception).isInstanceOf(NotFoundException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Specialization request not found.");
        Mockito.verify(this.specializationRequestGateway, Mockito.times(1)).findById(specializationId);
    }
}
