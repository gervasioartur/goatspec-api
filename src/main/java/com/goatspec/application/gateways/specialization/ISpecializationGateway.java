package com.goatspec.application.gateways.specialization;

import com.goatspec.domain.entities.specialization.Specialization;
import com.goatspec.domain.entities.specialization.SpecializationAndUser;

import java.util.List;

public interface ISpecializationGateway {
    Specialization create(Specialization specialization);
    List<SpecializationAndUser> getAll();
}
