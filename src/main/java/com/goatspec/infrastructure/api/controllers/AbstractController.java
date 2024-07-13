package com.goatspec.infrastructure.api.controllers;

import com.goatspec.infrastructure.api.dto.Response;
import com.goatspec.infrastructure.api.validation.ValidationComposite;
import com.goatspec.infrastructure.api.validation.validators.contract.IValidator;
import org.springframework.http.ResponseEntity;

import java.util.List;

public abstract class AbstractController<E> {
    public abstract ResponseEntity<Response> perform(E request);

    public List<IValidator> buildValidators(E request) {
        return List.of();
    }

    protected String validate(E request) {
        List<IValidator> validators = this.buildValidators(request);
        return new ValidationComposite(validators).validate();
    }
}