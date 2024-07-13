package com.goatspec.infrastructure.api.controllers;

import com.goatspec.infrastructure.api.validation.ValidationComposite;
import com.goatspec.infrastructure.api.validation.validators.contract.IValidator;

import java.util.List;

public abstract class AbstractController<E, T> {
    public abstract T perform(E entity);

    public List<IValidator> buildValidators(E request) {
        return List.of();
    }

    protected String validate(E request) {
        List<IValidator> validators = this.buildValidators(request);
        return new ValidationComposite(validators).validate();
    }
}