package com.goatspec.application.gateways.specialization;

import com.goatspec.domain.entities.specialization.Specialization;
import com.goatspec.domain.entities.specialization.SpecializationAndUser;

import java.util.List;
import java.util.UUID;

public interface ISpecializationGateway {
    Specialization findById(UUID id);

    void approve(UUID id);

    Specialization create(Specialization specialization);

    List<SpecializationAndUser> getAll();
}
