package com.goatspec.infrastructure.api.controllers.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goatspec.application.useCases.contracts.authentication.ISinginUseCase;
import com.goatspec.domain.entities.user.UserAccount;
import com.goatspec.domain.exceptions.UnauthorizedException;
import com.goatspec.infrastructure.api.dto.SinginRequest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class SinginControllerTests {
    private final String AUTH_API = "/auth/singin";

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @MockBean
    private ISinginUseCase singinUseCase;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    @DisplayName("Should return bad request if CPF is empty")
    void shouldReturnBadRequestIfCPFIsEmpty() throws Exception {
        SinginRequest request = new SinginRequest("", "any_password");
        String json = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(AUTH_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("The field 'CPF' is required!")));
    }

    @Test
    @DisplayName("Should return bad request if CPF is null")
    void shouldReturnBadRequestIfNullsEmpty() throws Exception {
        SinginRequest request = new SinginRequest(null, "any_password");
        String json = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(AUTH_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("The field 'CPF' is required!")));
    }

    @Test
    @DisplayName("Should return bad request if password is empty")
    void shouldReturnBadRequestIfPasswordIsEmpty() throws Exception {
        SinginRequest request = new SinginRequest("any_cpf", "");
        String json = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(AUTH_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("The field 'password' is required!")));
    }

    @Test
    @DisplayName("Should return bad request if password is null")
    void shouldReturnBadRequestIfPasswordIsNull() throws Exception {
        SinginRequest request = new SinginRequest("any_cpf", null);
        String json = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(AUTH_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("The field 'password' is required!")));
    }

    @Test
    @DisplayName("Should return unauthorized if user case returns unauthorized")
    void shouldReturnUnauthorizedIfUserCaseReturnsUnauthorized() throws Exception {
        SinginRequest request = new SinginRequest("any_cpf", "any_password");
        String json = new ObjectMapper().writeValueAsString(request);

        BDDMockito.given(this.singinUseCase.singin(request.cpf(), request.password())).willThrow(new UnauthorizedException("Bad credentials."));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(AUTH_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("body", Matchers.is("Bad credentials.")));

    }

    @Test
    @DisplayName("Should return internal server error if useCase throws")
    void shouldReturnInterServerErrorIfUseCaseThrows() throws Exception {
        SinginRequest request = new SinginRequest("any_cpf", "any_password");
        String json = new ObjectMapper().writeValueAsString(request);

        BDDMockito.doThrow(HttpServerErrorException.InternalServerError.class).when(this.singinUseCase).singin(request.cpf(), request.password());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(AUTH_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Should return userAccount on sing in success")
    void shouldReturnUserAccountOnSinginSuccess() throws Exception {
        SinginRequest request = new SinginRequest("any_cpf", "any_password");
        String json = new ObjectMapper().writeValueAsString(request);

        String accessToken = UUID.randomUUID().toString();

        BDDMockito.given(this.singinUseCase.singin(request.cpf(), request.password())).willReturn(new UserAccount(accessToken));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(AUTH_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("body.accessToken", Matchers.is(accessToken)));

    }

}
