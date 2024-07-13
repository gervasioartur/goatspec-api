package com.goatspec.domain.entities.specialization;

import java.math.BigDecimal;

public record SpecializationRequest(String cpf, String area, String type, int courseLoad, BigDecimal totalCost) {
}
