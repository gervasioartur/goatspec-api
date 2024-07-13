package com.goatspec.infrastructure.api.validation.validators;

import com.goatspec.infrastructure.api.validation.validators.contract.IValidator;

public abstract class AbstractValidator implements IValidator {
    protected String fieldName;
    protected Object fieldValue;

    @Override
    public String validate() {
        return null;
    }
}
