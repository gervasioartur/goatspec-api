package com.goatspec.infrastructure.api.validation.validators;

public class UserAccountRoleFieldValidator extends AbstractValidator {
    private final String returnMessage;

    public UserAccountRoleFieldValidator(String fieldName, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.returnMessage = "The option you entered is invalid! you must choose or TECHNICIAN account role and TEACHER account role.";
    }

    @Override
    public String validate() {
        String role = (String) fieldValue;
        if (!role.equals("TEACHER") && !role.equals("TECHNICIAN")) return returnMessage;
        return null;
    }
}