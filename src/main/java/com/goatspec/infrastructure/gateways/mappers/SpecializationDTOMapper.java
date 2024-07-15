package com.goatspec.infrastructure.gateways.mappers;

import com.goatspec.domain.entities.specialization.SpecializationRequest;
import com.goatspec.infrastructure.api.dto.CreateSpecializationRequest;

import java.util.UUID;

public class SpecializationDTOMapper {
    public SpecializationRequest toDomainObject(CreateSpecializationRequest request, UUID userId) {
        return new SpecializationRequest(userId, request.area(), request.type(), request.courseLoad(), request.totalCost());
    }
}
