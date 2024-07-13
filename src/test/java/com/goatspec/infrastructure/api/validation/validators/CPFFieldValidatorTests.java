package com.goatspec.infrastructure.api.validation.validators;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CPFFieldValidatorTests {
    @Test
    @DisplayName("should return error message if CPF is invalid")
    void shouldReturnErrorMessageIfCPFIsInvalid() {
        String fieldName = "cpf";
        String fieldValue = "00000000000";

        CPFFieldValidator validator = new CPFFieldValidator(fieldName, fieldValue);
        String result = validator.validate();
        Assertions.assertThat(result).isEqualTo("The 'CPF' is invalid, please verify your 'CPF' and try again!");

        fieldValue = "11111111111";
        validator = new CPFFieldValidator(fieldName, fieldValue);
        result = validator.validate();
        Assertions.assertThat(result).isEqualTo("The 'CPF' is invalid, please verify your 'CPF' and try again!");

        fieldValue = "22222222222";
        validator = new CPFFieldValidator(fieldName, fieldValue);
        result = validator.validate();
        Assertions.assertThat(result).isEqualTo("The 'CPF' is invalid, please verify your 'CPF' and try again!");

        fieldValue = "33333333333";
        validator = new CPFFieldValidator(fieldName, fieldValue);
        result = validator.validate();
        Assertions.assertThat(result).isEqualTo("The 'CPF' is invalid, please verify your 'CPF' and try again!");

        fieldValue = "44444444444";
        validator = new CPFFieldValidator(fieldName, fieldValue);
        result = validator.validate();
        Assertions.assertThat(result).isEqualTo("The 'CPF' is invalid, please verify your 'CPF' and try again!");

        fieldValue = "55555555555";
        validator = new CPFFieldValidator(fieldName, fieldValue);
        result = validator.validate();
        Assertions.assertThat(result).isEqualTo("The 'CPF' is invalid, please verify your 'CPF' and try again!");

        fieldValue = "66666666666";
        validator = new CPFFieldValidator(fieldName, fieldValue);
        result = validator.validate();
        Assertions.assertThat(result).isEqualTo("The 'CPF' is invalid, please verify your 'CPF' and try again!");

        fieldValue = "77777777777";
        validator = new CPFFieldValidator(fieldName, fieldValue);
        result = validator.validate();
        Assertions.assertThat(result).isEqualTo("The 'CPF' is invalid, please verify your 'CPF' and try again!");

        fieldValue = "88888888888";
        validator = new CPFFieldValidator(fieldName, fieldValue);
        result = validator.validate();
        Assertions.assertThat(result).isEqualTo("The 'CPF' is invalid, please verify your 'CPF' and try again!");

        fieldValue = "99999999999";
        validator = new CPFFieldValidator(fieldName, fieldValue);
        result = validator.validate();
        Assertions.assertThat(result).isEqualTo("The 'CPF' is invalid, please verify your 'CPF' and try again!");

        fieldValue = "98765432100";
        validator = new CPFFieldValidator(fieldName, fieldValue);
        result = validator.validate();
        Assertions.assertThat(result).isNull();

        fieldValue = "12345678a90";
        validator = new CPFFieldValidator(fieldName, fieldValue);
        result = validator.validate();
        Assertions.assertThat(result).isEqualTo("The 'CPF' is invalid, please verify your 'CPF' and try again!");

    }
}
