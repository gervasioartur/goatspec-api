package com.goatspec.application.useCases.implementations.authentication;

import com.goatspec.application.gateways.authentication.IAuthenticationGateway;
import com.goatspec.application.useCases.contracts.authentication.IGetLoggedUserInfoUseCase;
import com.goatspec.domain.entities.authentication.UserInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

@SpringBootTest
class IGetLoggedUserInfoUseCaseTests {
    private IGetLoggedUserInfoUseCase getLoggedUserInfoUseCase;
    @MockBean
    private IAuthenticationGateway authenticationGateway;

    @BeforeEach
    void setUp() {
        this.getLoggedUserInfoUseCase = new GetLoggedUserInfoUseCase(authenticationGateway);
    }

    @Test
    @DisplayName("Shoul return user info")
    void shouldReturnUserInfo() {
        UserInfo userInfo = new UserInfo(UUID.randomUUID(), "any_name", "any_email", "any_registration");
        Mockito.when(this.authenticationGateway.getLoggedUserInfo()).thenReturn(userInfo);
        UserInfo result = this.getLoggedUserInfoUseCase.get();
        Assertions.assertThat(result).isEqualTo(userInfo);
    }
}
