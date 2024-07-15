package com.goatspec.application.gateways.specialization;

import com.goatspec.domain.entities.specialization.SpecializationRequest;
import com.goatspec.domain.entities.specialization.SpecializationRequestInfo;

import java.util.List;
import java.util.UUID;

public interface ISpecializationRequestGateway {
    SpecializationRequest findById(UUID id);

    SpecializationRequestInfo approve(UUID id);

    SpecializationRequestInfo disapprove(UUID id);

    SpecializationRequest create(SpecializationRequest specializationRequest);

    List<SpecializationRequestInfo> getAll();
}
