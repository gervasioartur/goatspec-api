package com.goatspec.application.useCases.contracts.specialization;

import com.goatspec.domain.entities.specialization.SpecializationAndUser;

import java.util.List;

public interface IListAllSpecializationRequestsUseCase {
    List<SpecializationAndUser> getAll();
}
