package com.goatspec.domain.entities.specialization;

import java.math.BigDecimal;
import java.util.UUID;

public record SpecializationRequest(UUID userId, String area, String type, int courseLoad, BigDecimal totalCost) {
}
