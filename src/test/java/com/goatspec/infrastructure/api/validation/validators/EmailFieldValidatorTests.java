package com.goatspec.infrastructure.api.validation.validators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class EmailFieldValidatorTests {
    @Test
    @DisplayName("Should return error message if the emailfield is invalid")
    void shouldReturnErrorMessageIfTheEmailFieldIsInvalid() {
        String fieldName = "email";
        String fieldValue = "foo.bar@gmailcom ";

        EmailFieldValidator validator = new EmailFieldValidator(fieldName, fieldValue);
        String result = validator.validate();
        assertThat(result).isEqualTo("The 'E-mail' is invalid, please verify your 'E-mail' and try again!");
    }

    @Test
    @DisplayName("Should return null if the field is valid")
    void shouldReturnNullIfTheFieldIsValid() {
        String fieldName = "email";
        String fieldValue = "gervasio@gmail.com ";

        EmailFieldValidator validator = new EmailFieldValidator(fieldName, fieldValue);
        String result = validator.validate();
        assertThat(result).isNull();
    }
}
