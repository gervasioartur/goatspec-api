package com.goatspec.infrastructure.gateways.mappers;

import com.goatspec.domain.entities.specialization.Specialization;
import com.goatspec.infrastructure.api.dto.CreateSpecializationRequest;

import java.util.UUID;

public class SpecializationDTOMapper {
    public Specialization toDomainObject(CreateSpecializationRequest request, UUID userId) {
        return new Specialization(userId, request.area(), request.type(), request.courseLoad(), request.totalCost());
    }
}
