package com.goatspec.infrastructure.api.controllers.specialization;

import com.goatspec.application.useCases.contracts.specialization.IApproveSpecializationRequestUseCase;
import com.goatspec.application.useCases.contracts.specialization.IListAllSpecializationRequestsUseCase;
import com.goatspec.domain.entities.specialization.SpecializationRequestInfo;
import com.goatspec.domain.exceptions.NotFoundException;
import com.goatspec.infrastructure.persisntence.entities.SpecializationRequestEntity;
import com.goatspec.mocks.Mocks;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ApproveSpecializationRequestControllerTests {
    private final String SPEC_API = "/specs";

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @MockBean
    private IApproveSpecializationRequestUseCase approveSpecializationRequestUseCase;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    @DisplayName("Should return internal server error if the usecase throws")
    void shouldReturnInternalServerErrorIfUseCaseThrows() throws Exception {
        UUID specializationId = UUID.randomUUID();

        BDDMockito.doThrow(HttpServerErrorException.InternalServerError.class)
                .when(this.approveSpecializationRequestUseCase).approve(specializationId);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch(SPEC_API+"/approve/"+specializationId.toString())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Should return not found if the usecase throws")
    void shouldReturnNotFoundIfUseCaseThrows() throws Exception {
        UUID specializationId = UUID.randomUUID();

        BDDMockito.doThrow(new NotFoundException("Specialization not found."))
                .when(this.approveSpecializationRequestUseCase).approve(specializationId);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch(SPEC_API+"/approve/"+specializationId.toString())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("body", Matchers.is("Specialization not found.")));
    }

    @Test
    @DisplayName("Should return a success message")
    void shouldReturnSuccessMessage() throws Exception {
        UUID specializationId = UUID.randomUUID();
        BDDMockito.doNothing().when(this.approveSpecializationRequestUseCase).approve(specializationId);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch(SPEC_API+"/approve/"+specializationId.toString())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isOk());
    }
}
