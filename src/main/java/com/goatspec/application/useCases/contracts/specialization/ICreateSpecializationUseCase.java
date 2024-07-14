package com.goatspec.application.useCases.contracts.specialization;

import com.goatspec.domain.entities.specialization.Specialization;

public interface ICreateSpecializationUseCase {
    void create(Specialization specialization);
}
