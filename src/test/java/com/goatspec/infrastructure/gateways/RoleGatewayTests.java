package com.goatspec.infrastructure.gateways;

import com.goatspec.application.gateways.IRoleGateway;
import com.goatspec.application.gateways.IUserGateway;
import com.goatspec.domain.Enums.GenderEnum;
import com.goatspec.domain.Enums.RoleEnum;
import com.goatspec.domain.entities.Role;
import com.goatspec.domain.entities.User;
import com.goatspec.infrastructure.gateways.mappers.UserEntityMapper;
import com.goatspec.infrastructure.persisntence.entoties.RoleEntity;
import com.goatspec.infrastructure.persisntence.entoties.UserEntity;
import com.goatspec.infrastructure.persisntence.repositories.IRoleRepository;
import com.goatspec.infrastructure.persisntence.repositories.IUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

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
