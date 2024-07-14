package com.goatspec.infrastructure.api.validation.validators;

import com.goatspec.domain.Enums.SpecializationTypeEnum;

public class SpecializationAreaFieldValidator extends AbstractValidator {
    private final String returnMessage;

    public SpecializationAreaFieldValidator(String fieldName, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.returnMessage = "The option you entered is invalid! you must choose in "
                + SpecializationTypeEnum.POS_GRADUCAO.getValue()
                + " or " + SpecializationTypeEnum.MESTRADO.getValue()
                + " or " + SpecializationTypeEnum.DOUTORADO.getValue()
                + " or " + SpecializationTypeEnum.POSTGRADUATE.getValue()
                + " or " + SpecializationTypeEnum.MASTER_DEGREE.getValue()
                + " or " + SpecializationTypeEnum.DOCTORATE_DEGREE.getValue();

    }

    @Override
    public String validate() {
        String type = (String) fieldValue;
        if (!type.equalsIgnoreCase(SpecializationTypeEnum.POS_GRADUCAO.getValue())
                && !type.equalsIgnoreCase(SpecializationTypeEnum.MESTRADO.getValue())
                && !type.equalsIgnoreCase(SpecializationTypeEnum.DOUTORADO.getValue())
                && !type.equalsIgnoreCase(SpecializationTypeEnum.POSTGRADUATE.getValue())
                && !type.equalsIgnoreCase(SpecializationTypeEnum.MASTER_DEGREE.getValue())
                && !type.equalsIgnoreCase(SpecializationTypeEnum.DOCTORATE_DEGREE.getValue())
        )
            return returnMessage;
        return null;
    }
}