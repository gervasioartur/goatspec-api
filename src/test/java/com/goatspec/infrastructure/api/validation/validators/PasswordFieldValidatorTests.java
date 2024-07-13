package com.goatspec.infrastructure.api.validation.validators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class PasswordFieldValidatorTests {
    @Test
    @DisplayName("Should return error message if password field is not string")
    void shouldReturnErrorMessageIfPasswordFieldIsNotString() {
        String fieldName = "password";
        String fieldValue = "123456";

        PasswordFieldValidator validator = new PasswordFieldValidator(fieldName, fieldValue);
        String result = validator.validate();
        assertThat(result).isEqualTo("The 'password' must have minimum 6 and maximum 16 characters, at least one uppercase letter, one lowercase letter, one number and one special character!");
    }

    @Test
    @DisplayName("Should return null if password is strong")
    void shouldReturnNullIfPasswordIsString() {
        String fieldName = "password";
        String fieldValue = "Integraldecosxdx@0199";

        PasswordFieldValidator validator = new PasswordFieldValidator(fieldName, fieldValue);
        String result = validator.validate();
        assertThat(result).isNull();
    }
}