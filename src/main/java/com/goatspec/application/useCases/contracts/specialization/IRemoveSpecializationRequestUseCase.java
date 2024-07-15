package com.goatspec.application.useCases.contracts.specialization;

import java.util.UUID;

public interface IRemoveSpecializationRequestUseCase {
    void remove(UUID specializationId);
}
