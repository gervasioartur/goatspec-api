package com.goatspec.application.useCases.contracts.specialization;


import com.goatspec.domain.entities.specialization.SpecializationRequestInfo;

import java.util.List;
import java.util.UUID;

public interface IListAllUserSpecializationRequestUseCase {
    List<SpecializationRequestInfo> list(UUID userId);
}
