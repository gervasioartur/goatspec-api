package com.goatspec.infrastructure.api.validation.validators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class UserAccountRoleFieldValidatorTests {
    @Test
    @DisplayName("Should return error message if role is invalid")
    void shouldReturnErrorMessageIfRoleIsInvalid() {
        String fieldName = "role";
        String fieldValue = "invalid";
        UserAccountRoleFieldValidator validator = new UserAccountRoleFieldValidator(fieldName, fieldValue);
        String result = validator.validate();
        assertThat(result).isEqualTo("The option you entered is invalid! you must choose or TECHNICIAN account role and TEACHER account role.");
    }

    @Test
    @DisplayName("Should return null if role is TEACHER")
    void shouldReturnNullIfRoleIsTeacher() {
        String fieldName = "role";
        String fieldValue = "TEACHER";
        UserAccountRoleFieldValidator validator = new UserAccountRoleFieldValidator(fieldName, fieldValue);
        String result = validator.validate();
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Should return null if role is TECHNICIAN")
    void shouldReturnNullIfRoleIsTechnician() {
        String fieldName = "role";
        String fieldValue = "TECHNICIAN";
        UserAccountRoleFieldValidator validator = new UserAccountRoleFieldValidator(fieldName, fieldValue);
        String result = validator.validate();
        assertThat(result).isNull();
    }
}
