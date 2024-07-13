package com.goatspec.infrastructure.gateways;

import com.goatspec.application.gateways.IRoleGateway;
import com.goatspec.domain.entities.Role;
import com.goatspec.infrastructure.gateways.mappers.RoleEntityMapper;
import com.goatspec.infrastructure.persisntence.entities.RoleEntity;
import com.goatspec.infrastructure.persisntence.repositories.IRoleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

@SpringBootTest
class RoleGatewayTests {
    private IRoleGateway roleGateway;
    @MockBean
    private IRoleRepository roleRepository;
    @MockBean
    private RoleEntityMapper roleEntityMapper;

    @BeforeEach
    void setUp() {
        roleGateway = new RoleGateway(roleRepository, roleEntityMapper);
    }

    @Test
    @DisplayName("Should return null if role does not exist by name")
    void shouldReturnNullIfUserDoesNotExistByCpf() {
        String name = "any_name";
        Mockito.when(this.roleRepository.findByNameAndActive(name, true)).thenReturn(null);
        Role roleDomainObject = this.roleGateway.findRoleByName(name);
        Assertions.assertThat(roleDomainObject).isNull();
        Mockito.verify(this.roleRepository, Mockito.times(1)).findByNameAndActive(name, true);
    }

    @Test
    @DisplayName("Shoul return role domain object if exists by name")
    void shouldReturnRoleDomainObjectIfExistsByCpf() {
        String name = "any_name";

        Role toCreateRoleDomainObject = new Role(name);
        RoleEntity savedRoleEntity = RoleEntity.builder().id(UUID.randomUUID()).name(name).build();

        Mockito.when(this.roleRepository.findByNameAndActive(name, true)).thenReturn(savedRoleEntity);
        Mockito.when(this.roleEntityMapper.toDomainObject(savedRoleEntity)).thenReturn(toCreateRoleDomainObject);

        Role roleDomainObject = this.roleGateway.findRoleByName(name);

        Assertions.assertThat(roleDomainObject).isEqualTo(toCreateRoleDomainObject);
        Mockito.verify(this.roleRepository, Mockito.times(1)).findByNameAndActive(name, true);
    }
}
