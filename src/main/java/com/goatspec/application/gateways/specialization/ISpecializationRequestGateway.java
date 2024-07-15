package com.goatspec.application.gateways.specialization;

import com.goatspec.domain.entities.specialization.SpecializationRequest;
import com.goatspec.domain.entities.specialization.SpecializationRequestInfo;

import java.util.List;
import java.util.UUID;

public interface ISpecializationRequestGateway {
    SpecializationRequestInfo findById(UUID id);

    SpecializationRequestInfo findByIdAndUserId(UUID id,UUID userId);

    SpecializationRequestInfo approve(UUID id);

    void remove(UUID id);

    SpecializationRequestInfo disapprove(UUID id);

    SpecializationRequest create(SpecializationRequest specializationRequest);

    List<SpecializationRequestInfo> getAll();
}
