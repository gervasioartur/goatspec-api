package com.goatspec.infrastructure.gateways.mappers;

import com.goatspec.domain.entities.role.Role;
import com.goatspec.infrastructure.persisntence.entities.RoleEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RoleEntityMapperTests {
    private final RoleEntityMapper mapper = new RoleEntityMapper();

    @Test
    @DisplayName("Should return a role entity")
    void shouldReturnRoleEntity() {
        Role toCreateRoleDomainObject = new Role("any_name");
        RoleEntity toCreateRoleEntity = mapper.toRoleEntity(toCreateRoleDomainObject);
        Assertions.assertThat(toCreateRoleEntity.getName()).isEqualTo(toCreateRoleDomainObject.name());
    }

    @Test
    @DisplayName("Should return role domain object")
    void shouldReturnRoleDomainObject() {
        Role toCreateRoleDomainObject = new Role("any_name");
        RoleEntity toCreateRoleEntity = mapper.toRoleEntity(toCreateRoleDomainObject);
        toCreateRoleDomainObject = mapper.toDomainObject(toCreateRoleEntity);
        Assertions.assertThat(toCreateRoleEntity.getName()).isEqualTo(toCreateRoleDomainObject.name());
    }
}
