package com.goatspec.infrastructure.api.validation.validators;

import com.goatspec.domain.Enums.SpecializationRequestTypeEnum;

public class SpecializationAreaFieldValidator extends AbstractValidator {
    private final String returnMessage;

    public SpecializationAreaFieldValidator(String fieldName, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.returnMessage = "The option you entered is invalid! you must choose in "
                + SpecializationRequestTypeEnum.POS_GRADUCAO.getValue()
                + " or " + SpecializationRequestTypeEnum.MESTRADO.getValue()
                + " or " + SpecializationRequestTypeEnum.DOUTORADO.getValue()
                + " or " + SpecializationRequestTypeEnum.POSTGRADUATE.getValue()
                + " or " + SpecializationRequestTypeEnum.MASTER_DEGREE.getValue()
                + " or " + SpecializationRequestTypeEnum.DOCTORATE_DEGREE.getValue();

    }

    @Override
    public String validate() {
        String type = (String) fieldValue;
        if (!type.equalsIgnoreCase(SpecializationRequestTypeEnum.POS_GRADUCAO.getValue())
                && !type.equalsIgnoreCase(SpecializationRequestTypeEnum.MESTRADO.getValue())
                && !type.equalsIgnoreCase(SpecializationRequestTypeEnum.DOUTORADO.getValue())
                && !type.equalsIgnoreCase(SpecializationRequestTypeEnum.POSTGRADUATE.getValue())
                && !type.equalsIgnoreCase(SpecializationRequestTypeEnum.MASTER_DEGREE.getValue())
                && !type.equalsIgnoreCase(SpecializationRequestTypeEnum.DOCTORATE_DEGREE.getValue())
        )
            return returnMessage;
        return null;
    }
}