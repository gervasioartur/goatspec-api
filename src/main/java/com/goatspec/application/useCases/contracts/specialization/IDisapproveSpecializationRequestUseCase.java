package com.goatspec.application.useCases.contracts.specialization;

import java.util.UUID;

public interface IDisapproveSpecializationRequestUseCase {
    void disapprove(UUID specializationId);
}
