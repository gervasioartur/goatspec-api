package com.goatspec.application.useCases.contracts.specialization;

import com.goatspec.domain.entities.specialization.SpecializationRequestInfo;

import java.util.List;

public interface IListAllSpecializationRequestsUseCase {
    List<SpecializationRequestInfo> getAll();
}
