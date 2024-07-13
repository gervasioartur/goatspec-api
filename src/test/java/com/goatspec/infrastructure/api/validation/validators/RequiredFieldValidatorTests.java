package com.goatspec.infrastructure.api.validation.validators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class RequiredFieldValidatorTests {
    @Test
    @DisplayName("Should validate if the string is empty")
    void shouldValidateIfTheStringIEmpty() {
        String fieldlName = "name";
        String fieldlValue = "";

        RequiredFieldValidator validator = new RequiredFieldValidator(fieldlName, fieldlValue);
        String result = validator.validate();
        assertThat(result).isEqualTo("The field 'name' is required!");
    }

    @Test
    @DisplayName("Should validate if integer is zero")
    void shouldValidateIfTheIntegerIsZero() {
        String fieldlName = "name";
        Integer fieldlValue = 0;

        RequiredFieldValidator validator = new RequiredFieldValidator(fieldlName, fieldlValue);
        String result = validator.validate();
        assertThat(result).isEqualTo("The field 'name' is required!");
    }

    @Test
    @DisplayName("Should validate return null if integer is greater than 0")
    void shouldReturnNullIfIntegerIsGreaterThanZero() {
        String fieldlName = "name";
        Integer fieldlValue = 1;

        RequiredFieldValidator validator = new RequiredFieldValidator(fieldlName, fieldlValue);
        String result = validator.validate();
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Should validate if the Object is null")
    void shouldValidateIfTheObjectIsNull() {
        String fieldlName = "name";
        Object fieldlValue = null;

        RequiredFieldValidator validator = new RequiredFieldValidator(fieldlName, fieldlValue);
        String result = validator.validate();
        assertThat(result).isEqualTo("The field 'name' is required!");
    }

    @Test
    @DisplayName("Should validate return null if the object is not null")
    void shouldReturnNullIfObjetIsNotNull() {
        String fieldlName = "name";
        Object fieldlValue = new Object();

        RequiredFieldValidator validator = new RequiredFieldValidator(fieldlName, fieldlValue);
        String result = validator.validate();
        assertThat(result).isNull();
    }
}
