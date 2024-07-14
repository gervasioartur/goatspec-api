package com.goatspec.domain.entities.specialization;

import java.math.BigDecimal;
import java.util.UUID;

public record Specialization(UUID userId, String area, String type, int courseLoad, BigDecimal totalCost) {
}
