package com.goatspec.domain.entities.specialization;

import java.math.BigDecimal;

public record SpecializationRequestInfo(
        String userName,
        String userEmail,
        String registration,
        String specializationRequestId,
        String area,
        String type,
        int courseLoad,
        BigDecimal totalCost,
        String specializationStatus
) {
}
