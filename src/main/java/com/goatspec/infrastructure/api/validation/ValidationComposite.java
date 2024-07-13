package com.goatspec.infrastructure.api.validation;

import com.goatspec.infrastructure.api.validation.validators.contract.IValidator;

import java.util.List;

public class ValidationComposite {
    private final List<IValidator> validators;

    public ValidationComposite(List<IValidator> validators) {
        this.validators = validators;
    }

    public String validate() {
        for (IValidator validator : validators) {
            String error = validator.validate();
            if (error != null)
                return error;
        }
        return null;
    }
}