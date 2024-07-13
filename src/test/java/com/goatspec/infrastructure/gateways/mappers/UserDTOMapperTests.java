package com.goatspec.infrastructure.gateways.mappers;

import com.goatspec.domain.Enums.GenderEnum;
import com.goatspec.domain.Enums.RoleEnum;
import com.goatspec.domain.entities.user.User;
import com.goatspec.infrastructure.api.dto.CreateUserRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class UserDTOMapperTests {
    private final UserDTOMapper mapper = new UserDTOMapper();

    @Test
    @DisplayName("Should return user domain object")
    void shouldReturnUserDomainObject() {
        CreateUserRequest request = new CreateUserRequest("any_cpf", "any_email", "any_create_registration", "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "any_password");

        User userDomainObject = mapper.toDomainObject(request);

        Assertions.assertThat(request.cpf()).isEqualTo(userDomainObject.cpf());
        Assertions.assertThat(request.email()).isEqualTo(userDomainObject.email());
        Assertions.assertThat(request.registration()).isEqualTo(userDomainObject.registration());
        Assertions.assertThat(request.name()).isEqualTo(userDomainObject.name());
        Assertions.assertThat(request.dateOfBirth()).isEqualTo(userDomainObject.dateOfBirth());
        Assertions.assertThat(request.role()).isEqualTo(userDomainObject.role());
        Assertions.assertThat(request.password()).isEqualTo(userDomainObject.password());
    }
}
