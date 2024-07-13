package com.goatspec.infrastructure.gateways.mappers;

import com.goatspec.domain.Enums.GenderEnum;
import com.goatspec.domain.Enums.RoleEnum;
import com.goatspec.domain.entities.Role;
import com.goatspec.domain.entities.User;
import com.goatspec.infrastructure.persisntence.entoties.RoleEntity;
import com.goatspec.infrastructure.persisntence.entoties.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class RoleEntityMapperTests {
    private final RoleEntityMapper mapper = new RoleEntityMapper();

    @Test
    @DisplayName("Should return a role entity")
    void shouldReturnUserEntity() {
        Role toCreateRoleDomainObject = new Role("any_name");
        RoleEntity toCreateRoleEntity = mapper.toRoleEntity(toCreateRoleDomainObject);
        Assertions.assertThat(toCreateRoleEntity.getName()).isEqualTo(toCreateRoleDomainObject.name());
    }
}
