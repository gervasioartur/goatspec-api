package com.goatspec.application.useCases.implementations.specialization;

import com.goatspec.application.gateways.specialization.ISpecializationRequestGateway;
import com.goatspec.application.useCases.contracts.specialization.IRemoveSpecializationRequestUseCase;
import com.goatspec.domain.Enums.SpecializationRequestStatusEnum;
import com.goatspec.domain.entities.specialization.SpecializationRequestInfo;
import com.goatspec.domain.exceptions.BusinessException;
import com.goatspec.domain.exceptions.NotFoundException;

import java.util.Objects;
import java.util.UUID;

public class RemoveSpecializationRequestUseCaseUseCase implements IRemoveSpecializationRequestUseCase {
    private final ISpecializationRequestGateway specializationRequestGateway;

    public RemoveSpecializationRequestUseCaseUseCase(ISpecializationRequestGateway specializationRequestGateway) {
        this.specializationRequestGateway = specializationRequestGateway;
    }

    @Override
    public void remove(UUID specializationId) {
        SpecializationRequestInfo result =  this.specializationRequestGateway.findById(specializationId);
        if (!Objects.equals(result.specializationStatus(), SpecializationRequestStatusEnum.PENDING.getValue()))
            throw new BusinessException("You only can remove specialization request on pending status.");
        this.specializationRequestGateway.remove(specializationId);
    }
}
