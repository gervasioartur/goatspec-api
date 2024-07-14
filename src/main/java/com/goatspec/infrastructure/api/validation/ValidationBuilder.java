package com.goatspec.infrastructure.api.validation;

import com.goatspec.infrastructure.api.validation.validators.*;
import com.goatspec.infrastructure.api.validation.validators.contract.IValidator;

import java.util.ArrayList;
import java.util.List;

public class ValidationBuilder {
    private final List<IValidator> validators = new ArrayList<>();
    private final String fieldName;
    private final Object fieldValue;

    private ValidationBuilder(String fieldName, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public static ValidationBuilder of(String fieldName, Object fieldValue) {
        return new ValidationBuilder(fieldName, fieldValue);
    }

    public ValidationBuilder required() {
        this.validators.add(new RequiredFieldValidator(this.fieldName, this.fieldValue));
        return this;
    }

    public ValidationBuilder cpf() {
        this.validators.add(new CPFFieldValidator(this.fieldName, this.fieldValue));
        return this;
    }

    public ValidationBuilder email() {
        this.validators.add(new EmailFieldValidator(this.fieldName, this.fieldValue));
        return this;
    }

    public ValidationBuilder role() {
        this.validators.add(new UserAccountRoleFieldValidator(this.fieldName, this.fieldValue));
        return this;
    }

    public ValidationBuilder password() {
        this.validators.add(new PasswordFieldValidator(this.fieldName, this.fieldValue));
        return this;
    }

    public ValidationBuilder specType() {
        this.validators.add(new SpecializationAreaFieldValidator(this.fieldName, this.fieldValue));
        return this;
    }

    public ValidationBuilder min(int min) {
        this.validators.add(new MinFieldValidator(this.fieldName, this.fieldValue,min));
        return this;
    }



    public List<IValidator> build() {
        return this.validators;
    }
}