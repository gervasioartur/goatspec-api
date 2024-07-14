package com.goatspec.application.gateways.specialization;

import com.goatspec.domain.entities.specialization.Specialization;

public interface ISpecializationGateway {
    Specialization create(Specialization specialization);
}
