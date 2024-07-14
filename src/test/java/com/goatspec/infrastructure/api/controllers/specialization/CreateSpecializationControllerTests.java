package com.goatspec.infrastructure.api.controllers.specialization;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goatspec.application.useCases.contracts.authentication.IGetLoggedUserInfoUseCase;
import com.goatspec.application.useCases.contracts.specialization.ICreateSpecializationUseCase;
import com.goatspec.domain.Enums.SpecializationTypeEnum;
import com.goatspec.domain.entities.authentication.UserInfo;
import com.goatspec.domain.entities.specialization.Specialization;
import com.goatspec.domain.exceptions.UnauthorizedException;
import com.goatspec.infrastructure.api.dto.CreateSpecializationRequest;
import com.goatspec.infrastructure.gateways.mappers.SpecializationDTOMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class CreateSpecializationControllerTests {
    private final String SPEC_API = "/specs";

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @MockBean
    private ICreateSpecializationUseCase createSpecializationUseCase;
    @MockBean
    private SpecializationDTOMapper specializationDTOMapper;
    @MockBean
    private IGetLoggedUserInfoUseCase getLoggedUserInfoUseCase;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    @DisplayName("Should return bad request if area is empty")
    void shouldReturnBadRequestIfCPFIsEmpty() throws Exception {
        CreateSpecializationRequest request = new CreateSpecializationRequest("", "any_type", 2, new BigDecimal("25"));
        String json = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(SPEC_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("The field 'area' is required!")));
    }

    @Test
    @DisplayName("Should return bad request if area is null")
    void shouldReturnBadRequestIfCPFIsNull() throws Exception {
        CreateSpecializationRequest request = new CreateSpecializationRequest(null, "any_type", 2, new BigDecimal("25"));
        String json = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(SPEC_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("The field 'area' is required!")));
    }

    @Test
    @DisplayName("Should return bad request if type is empty")
    void shouldReturnBadRequestIfTypeIsEmpty() throws Exception {
        CreateSpecializationRequest request = new CreateSpecializationRequest("any_area", "", 2, new BigDecimal("25"));
        String json = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(SPEC_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("The field 'type' is required!")));
    }

    @Test
    @DisplayName("Should return bad request if type is null")
    void shouldReturnBadRequestIfTypeIsNull() throws Exception {
        CreateSpecializationRequest request = new CreateSpecializationRequest("any_area", null, 2, new BigDecimal("25"));
        String json = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(SPEC_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("The field 'type' is required!")));
    }

    @Test
    @DisplayName("Should return bad request if type is invalid")
    void shouldReturnBadRequestIfTypeIsInvalid() throws Exception {
        CreateSpecializationRequest request = new CreateSpecializationRequest("any_area", "any_ty", 2, new BigDecimal("25"));
        String json = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(SPEC_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("The option you entered is invalid! you must choose in POS GRADUAÇÃO or MESTRADO or DOUTORADO or POSTGRADUATE or MASTER'S DEGREE or DOCTORATE DEGREE")));
    }

    @Test
    @DisplayName("Should return bad request  of CourseLoad  less ou equal zero")
    void shouldReturnBadRequestIfCourseLoadIsInvalid() throws Exception {
        CreateSpecializationRequest request = new CreateSpecializationRequest("any_area", SpecializationTypeEnum.DOCTORATE_DEGREE.getValue(), 0, new BigDecimal("25"));
        String json = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(SPEC_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("The field 'course load' must be greater  than " + 60 + "!")));
    }

    @Test
    @DisplayName("Should return unauthorized if user does not exists")
    void shouldReturnUnauthorizedIfUserDoesNotExist() throws Exception {
        CreateSpecializationRequest request = new CreateSpecializationRequest("any_area", SpecializationTypeEnum.DOCTORATE_DEGREE.getValue(), 60, new BigDecimal("25"));

        UserInfo userInfoDomainObject = new UserInfo(UUID.randomUUID(),"any_name", "any_email", "any_registration");
        Specialization specializationDomainObject = new Specialization(userInfoDomainObject.id(),"any_area", SpecializationTypeEnum.DOCTORATE_DEGREE.getValue(), 60, new BigDecimal("25"));

        BDDMockito.when(this.getLoggedUserInfoUseCase.get()).thenReturn(userInfoDomainObject);
        BDDMockito.when(this.specializationDTOMapper.toDomainObject(request,userInfoDomainObject.id())).thenReturn(specializationDomainObject);
        BDDMockito.doThrow(new UnauthorizedException("Something went wrong! please try again later!"))
                .when(this.createSpecializationUseCase).create(specializationDomainObject);


        String json = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(SPEC_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);


        mvc
                .perform(requestBuilder)
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("body", Matchers.is("Something went wrong! please try again later!")));

    }
}
