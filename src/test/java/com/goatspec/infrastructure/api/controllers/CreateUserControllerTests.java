package com.goatspec.infrastructure.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goatspec.application.useCases.contracts.ICreateUserUseCase;
import com.goatspec.domain.Enums.GenderEnum;
import com.goatspec.domain.Enums.RoleEnum;
import com.goatspec.domain.entities.user.User;
import com.goatspec.domain.exceptions.BusinessException;
import com.goatspec.infrastructure.api.dto.CreateUserRequest;
import com.goatspec.infrastructure.gateways.mappers.UserDTOMapper;
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
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CreateUserControllerTests {
    private final String USER_API = "/users";

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
        CreateUserRequest request = new CreateUserRequest("", "any_email", "any_registration",
                "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "any_password");

        String json = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(USER_API)
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
    void shouldReturnBadRequestIfCPFIsNull() throws Exception {
        CreateUserRequest request = new CreateUserRequest(null, "any_email", "any_registration",
                "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "any_password");

        String json = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(USER_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("The field 'CPF' is required!")));
    }

    @Test
    @DisplayName("Should return bad request if email is invalid")
    void shouldReturnBadRequestIfCPFIsInvalid() throws Exception {
        CreateUserRequest request = new CreateUserRequest("any_cpf", "any_email", "any_registration",
                "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "any_password");

        String json = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(USER_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("The 'CPF' is invalid, please verify your 'CPF' and try again!")));
    }

    @Test
    @DisplayName("Should return bad request if email is empty")
    void shouldReturnBadRequestIfEmailIsEmpty() throws Exception {
        CreateUserRequest request = new CreateUserRequest("32635892024", "", "any_registration",
                "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "any_password");

        String json = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(USER_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("The field 'E-mail' is required!")));
    }

    @Test
    @DisplayName("Should return bad request if email is null")
    void shouldReturnBadRequestIfEmailIsNull() throws Exception {
        CreateUserRequest request = new CreateUserRequest("32635892024", null, "any_registration",
                "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "any_password");

        String json = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(USER_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("The field 'E-mail' is required!")));
    }

    @Test
    @DisplayName("Should return bad request if email is invalid")
    void shouldReturnBadRequestIfEmailIsInvalid() throws Exception {
        CreateUserRequest request = new CreateUserRequest("32635892024", "any_email", "any_registration",
                "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "any_password");

        String json = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(USER_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("The 'E-mail' is invalid, please verify your 'E-mail' and try again!")));
    }

    @Test
    @DisplayName("Should return bad request if registration is empty")
    void shouldReturnBadRequestIfRegistrationIsEmpty() throws Exception {
        CreateUserRequest request = new CreateUserRequest("32635892024", "gervasio@gmail.com", "",
                "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "any_password");

        String json = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(USER_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("The field 'registration' is required!")));
    }

    @Test
    @DisplayName("Should return bad request if registration is null")
    void shouldReturnBadRequestIfRegistrationIsNull() throws Exception {
        CreateUserRequest request = new CreateUserRequest("32635892024", "gervasio@gmail.com", null,
                "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "any_password");

        String json = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(USER_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("The field 'registration' is required!")));
    }

    @Test
    @DisplayName("Should return bad request if name is empty")
    void shouldReturnBadRequestIfNameIsEmpty() throws Exception {
        CreateUserRequest request = new CreateUserRequest("32635892024", "gervasio@gmail.com", "any_registration",
                "", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "any_password");

        String json = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(USER_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("The field 'name' is required!")));
    }

    @Test
    @DisplayName("Should return bad request if name is null")
    void shouldReturnBadRequestIfNameIsNull() throws Exception {
        CreateUserRequest request = new CreateUserRequest("32635892024", "gervasio@gmail.com", "any_registration",
                null, new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "any_password");

        String json = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(USER_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("The field 'name' is required!")));
    }

    @Test
    @DisplayName("Should return bad request if date of birth is null")
    void shouldReturnBadRequestIfDateOfBirthIsNull() throws Exception {
        CreateUserRequest request = new CreateUserRequest("32635892024", "gervasio@gmail.com", "any_registration",
                "any_name", null, GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "any_password");

        String json = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(USER_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("The field 'date of birth' is required!")));
    }

    @Test
    @DisplayName("Should return bad request if gender is empty")
    void shouldReturnBadRequestIfGenderIsEmpty() throws Exception {
        CreateUserRequest request = new CreateUserRequest("32635892024", "gervasio@gmail.com", "any_registration",
                "any_name", new Date(), "", RoleEnum.TEACHER.getValue(), "any_password");

        String json = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(USER_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("The field 'gender' is required!")));
    }

    @Test
    @DisplayName("Should return bad request if gender is null")
    void shouldReturnBadRequestIfGenderIsNull() throws Exception {
        CreateUserRequest request = new CreateUserRequest("32635892024", "gervasio@gmail.com", "any_registration",
                "any_name", new Date(), null, RoleEnum.TEACHER.getValue(), "any_password");

        String json = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(USER_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("The field 'gender' is required!")));
    }

    @Test
    @DisplayName("Should return bad request if role is empty")
    void shouldReturnBadRequestIfRoleIsEmpty() throws Exception {
        CreateUserRequest request = new CreateUserRequest("32635892024", "gervasio@gmail.com", "any_registration",
                "any_name", new Date(), GenderEnum.MALE.getValue(), "", "any_password");

        String json = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(USER_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("The field 'role' is required!")));
    }

    @Test
    @DisplayName("Should return bad request if role is null")
    void shouldReturnBadRequestIfRoleIsNull() throws Exception {
        CreateUserRequest request = new CreateUserRequest("32635892024", "gervasio@gmail.com", "any_registration",
                "any_name", new Date(), GenderEnum.MALE.getValue(), null, "any_password");

        String json = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(USER_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("The field 'role' is required!")));
    }

    @Test
    @DisplayName("Should return bad request if role is invalid")
    void shouldReturnBadRequestIfRoleIsInvalid() throws Exception {
        CreateUserRequest request = new CreateUserRequest("32635892024", "gervasio@gmail.com", "any_registration",
                "any_name", new Date(), GenderEnum.MALE.getValue(), "any_role", "any_password");

        String json = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(USER_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("The option you entered is invalid! you must choose or TECHNICIAN account role and TEACHER account role.")));
    }

    @Test
    @DisplayName("Should return bad request if password is empty")
    void shouldReturnBadRequestIfPasswordIsEmpty() throws Exception {
        CreateUserRequest request = new CreateUserRequest("32635892024", "gervasio@gmail.com", "any_registration",
                "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "");

        String json = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(USER_API)
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
        CreateUserRequest request = new CreateUserRequest("32635892024", "gervasio@gmail.com", "any_registration",
                "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), null);

        String json = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(USER_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("The field 'password' is required!")));
    }

    @Test
    @DisplayName("Should return bad request if password is invalid")
    void shouldReturnBadRequestIfPasswordIsInvalid() throws Exception {
        CreateUserRequest request = new CreateUserRequest("32635892024", "gervasio@gmail.com", "any_registration",
                "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "any_password");

        String json = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(USER_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("The 'password' must have minimum 6 and maximum 16 characters, at least one uppercase letter, one lowercase letter, one number and one special character!")));
    }

    @Test
    @DisplayName("Should return bad request if CPF  is already taken")
    void shouldReturnBadRequestIfCPFIsAlreadyTaken() throws Exception {
        CreateUserRequest request = new CreateUserRequest("32635892024", "gervasio@gmail.com", "any_registration",
                "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "Gervasio@0199");

        User userDomainObject = new User("32635892024", "gervasio@gmail.com", "any_registration",
                "any_name", request.dateOfBirth(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "Gervasio@0199");

        String json = new ObjectMapper().writeValueAsString(request);

        BDDMockito.given(this.userDTOMapper.toUserDomainObject(request)).willReturn(userDomainObject);
        BDDMockito.given(this.createUserUseCase.create(userDomainObject)).willThrow(new BusinessException("The CPF is already in use. Please try to sing in with credentials."));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(USER_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("The CPF is already in use. Please try to sing in with credentials.")));
    }

    @Test
    @DisplayName("Should return bad request if email  is already taken")
    void shouldReturnBadRequestIfEmailIsAlreadyTaken() throws Exception {
        CreateUserRequest request = new CreateUserRequest("32635892024", "gervasio@gmail.com", "any_registration",
                "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "Gervasio@0199");

        User userDomainObject = new User("32635892024", "gervasio@gmail.com", "any_registration",
                "any_name", request.dateOfBirth(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "Gervasio@0199");

        String json = new ObjectMapper().writeValueAsString(request);

        BDDMockito.given(this.userDTOMapper.toUserDomainObject(request)).willReturn(userDomainObject);
        BDDMockito.given(this.createUserUseCase.create(userDomainObject)).willThrow(new BusinessException("The email is already in use. Please try another email."));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(USER_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("The email is already in use. Please try another email.")));
    }

    @Test
    @DisplayName("Should return bad request if registration  is already taken")
    void shouldReturnBadRequestIfRegistrationIsAlreadyTaken() throws Exception {
        CreateUserRequest request = new CreateUserRequest("32635892024", "gervasio@gmail.com", "any_registration",
                "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "Gervasio@0199");

        User userDomainObject = new User("32635892024", "gervasio@gmail.com", "any_registration",
                "any_name", request.dateOfBirth(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "Gervasio@0199");

        String json = new ObjectMapper().writeValueAsString(request);

        BDDMockito.given(this.userDTOMapper.toUserDomainObject(request)).willReturn(userDomainObject);
        BDDMockito.given(this.createUserUseCase.create(userDomainObject)).willThrow(new BusinessException("The registration is already in use. Please try another registration."));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(USER_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("The registration is already in use. Please try another registration.")));
    }

    @Test
    @DisplayName("Should return bad request if use case throws unexpected exception")
    void shouldReturnBadRequestIfUseCaseThrowsUnexpectedException() throws Exception {
        CreateUserRequest request = new CreateUserRequest("32635892024", "gervasio@gmail.com", "any_registration",
                "any_name", new Date(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "Gervasio@0199");

        User userDomainObject = new User("32635892024", "gervasio@gmail.com", "any_registration",
                "any_name", request.dateOfBirth(), GenderEnum.MALE.getValue(), RoleEnum.TEACHER.getValue(), "Gervasio@0199");

        String json = new ObjectMapper().writeValueAsString(request);

        BDDMockito.given(this.userDTOMapper.toUserDomainObject(request)).willReturn(userDomainObject);
        BDDMockito.given(this.createUserUseCase.create(userDomainObject)).willThrow(new BusinessException("The registration is already in use. Please try another registration."));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(USER_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("The registration is already in use. Please try another registration.")));
    }
}
