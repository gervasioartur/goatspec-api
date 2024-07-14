package com.goatspec.application.useCases.implementations.specialization;

import com.goatspec.application.gateways.specialization.ISpecializationGateway;
import com.goatspec.application.gateways.user.IUserGateway;
import com.goatspec.application.useCases.contracts.specialization.ICreateSpecializationUseCase;
import com.goatspec.domain.Enums.GenderEnum;
import com.goatspec.domain.Enums.RoleEnum;
import com.goatspec.domain.entities.specialization.Specialization;
import com.goatspec.domain.entities.user.User;
import com.goatspec.domain.exceptions.UnauthorizedException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@SpringBootTest
class CreateSpecializationUseCaseTests {
    private ICreateSpecializationUseCase createSpecializationUseCase;
    @MockBean
    private IUserGateway userGateway;
    @MockBean
    private ISpecializationGateway specializationGateway;

    @BeforeEach
    void setUp() {
        this.createSpecializationUseCase = new
                CreateSpecializationUseCase(userGateway, specializationGateway);
    }

    @Test
    @DisplayName("Should throw unauthorized exception if user does not exist")
    void shouldHowBusinessExceptionIfUserDoesNotExist() {
        Specialization specialization = new
                Specialization(UUID.randomUUID(), "any_area", "any_type", 2, new BigDecimal("20"));

        Mockito.when(this.userGateway.findUserById(specialization.userId())).thenReturn(null);

        Throwable exception = Assertions.catchThrowable(() -> this.createSpecializationUseCase.create(specialization));

        Assertions.assertThat(exception).isInstanceOf(UnauthorizedException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Something went wrong! please try again later!");
        Mockito.verify(this.userGateway, Mockito.times(1)).findUserById(specialization.userId());
    }

    @Test
    @DisplayName("should create ne specialization request")
    void shouldCreateSpecializationRequest() {
        Specialization specialization = new
                Specialization(UUID.randomUUID(), "any_area", "any_type", 2, new BigDecimal("20"));

        User createUserDomainObject = new User("any_cpf", "any_email", "any_registration", "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "any_password");
        Mockito.when(this.userGateway.findUserById(specialization.userId())).thenReturn(createUserDomainObject);
        Mockito.when(this.specializationGateway.create(specialization)).thenReturn(specialization);

        this.createSpecializationUseCase.create(specialization);
        Mockito.verify(this.userGateway, Mockito.times(1)).findUserById(specialization.userId());
        Mockito.verify(this.specializationGateway, Mockito.times(1)).create(specialization);
    }
}
