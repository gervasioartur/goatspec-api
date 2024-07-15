package com.goatspec.application.useCases.contracts.specialization;

import java.util.UUID;

public interface IApproveSpecializationRequestUseCase {
    void approve(UUID specializationId);
}
