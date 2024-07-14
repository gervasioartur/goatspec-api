package com.goatspec.infrastructure.api.dto;

import java.math.BigDecimal;

public record CreateSpecializationRequest(String area, String type, int courseLoad, BigDecimal totalCost) {
}
