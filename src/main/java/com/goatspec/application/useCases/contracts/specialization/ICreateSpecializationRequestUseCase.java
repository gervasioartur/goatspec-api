package com.goatspec.application.useCases.contracts.specialization;

import com.goatspec.domain.entities.specialization.SpecializationRequest;

public interface ICreateSpecializationRequestUseCase {
    void create(SpecializationRequest specializationRequest);
}
