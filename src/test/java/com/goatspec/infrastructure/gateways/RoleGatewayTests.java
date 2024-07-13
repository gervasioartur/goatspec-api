package com.goatspec.infrastructure.gateways;

import com.goatspec.application.gateways.IRoleGateway;
import com.goatspec.domain.entities.Role;
import com.goatspec.infrastructure.persisntence.repositories.IRoleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class RoleGatewayTests {
    private IRoleGateway roleGateway;
    @MockBean
    private IRoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        roleGateway = new RoleGateway(roleRepository);
    }

    @Test
    @DisplayName("Should return null if role does not exist by name")
    void shouldReturnNullIfUserDoesNotExistByCpf() {
        String name = "any_any";
        Mockito.when(this.roleRepository.findByNameAndActive(name, true)).thenReturn(null);
        Role roleDomainObject = this.roleGateway.findRoleByName(name);
        Assertions.assertThat(roleDomainObject).isNull();
        Mockito.verify(this.roleRepository, Mockito.times(1)).findByNameAndActive(name, true);
    }
}
