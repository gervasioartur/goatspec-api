package com.goatspec.application.useCases.contracts.specialization;

import java.util.UUID;

public interface IRemoveSpecializationRequest {
    void remove(UUID specializationId);
}
