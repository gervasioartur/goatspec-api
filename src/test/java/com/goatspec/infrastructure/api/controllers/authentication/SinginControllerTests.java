package com.goatspec.infrastructure.api.controllers.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goatspec.application.useCases.contracts.user.ICreateUserUseCase;
import com.goatspec.domain.Enums.GenderEnum;
import com.goatspec.domain.Enums.RoleEnum;
import com.goatspec.infrastructure.api.dto.CreateUserRequest;
import com.goatspec.infrastructure.api.dto.SinginRequest;
import com.goatspec.infrastructure.gateways.mappers.UserDTOMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class SinginControllerTests {
    private final String AUTH_API = "/auth/singin";

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @MockBean
    private ICreateUserUseCase createUserUseCase;
    @MockBean
    private UserDTOMapper userDTOMapper;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    @DisplayName("Should return bad request if CPF is empty")
    void shouldReturnBadRequestIfCPFIsEmpty() throws Exception {
        SinginRequest request =  new SinginRequest("","any_password");
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
        SinginRequest request =  new SinginRequest(null,"any_password");
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
        SinginRequest request =  new SinginRequest("any_cpf","");
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
        SinginRequest request =  new SinginRequest("any_cpf",null);
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

}
