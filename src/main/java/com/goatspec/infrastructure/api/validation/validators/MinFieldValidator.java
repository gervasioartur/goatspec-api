package com.goatspec.infrastructure.api.validation.validators;

public class MinFieldValidator extends AbstractValidator {
    private final String returnMessage;
    private final int min;

    public MinFieldValidator(String fieldName, Object fieldValue, int min) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.min = min;
        this.returnMessage = "The field '" + this.fieldName + "' must be greater  than " + this.min + "!";
    }

    @Override
    public String validate() {
        double value = (int) this.fieldValue;
        if (value < min)
            return returnMessage;
        return null;
    }
}