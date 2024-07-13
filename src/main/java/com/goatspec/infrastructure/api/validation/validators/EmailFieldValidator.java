package com.goatspec.infrastructure.api.validation.validators;

import com.goatspec.domain.Enums.GeneralEnumText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailFieldValidator extends AbstractValidator {
    public EmailFieldValidator(String fieldName, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    @Override
    public String validate() {
        String value = (String) this.fieldValue;
        value = value.trim();

        String regex = GeneralEnumText.EMAIL_PASSWORD_EXPRESSION.getValue();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches())
            return "The 'E-mail' is invalid, please verify your 'E-mail' and try again!";
        return null;
    }

}