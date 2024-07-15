package com.goatspec.application.gateways.specialization;

import com.goatspec.domain.entities.specialization.SpecializationRequestInfo;
import com.goatspec.domain.entities.specialization.SpecializationRequest;
import com.goatspec.domain.entities.specialization.SpecializationRequestAndUser;

import java.util.List;
import java.util.UUID;

public interface ISpecializationRequestGateway {
    SpecializationRequest findById(UUID id);

    SpecializationRequestAndUser approve(UUID id);

    SpecializationRequest create(SpecializationRequest specializationRequest);

    List<SpecializationRequestInfo> getAll();
}
