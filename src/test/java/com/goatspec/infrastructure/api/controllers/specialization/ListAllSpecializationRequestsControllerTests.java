package com.goatspec.infrastructure.api.controllers.specialization;

import com.goatspec.application.useCases.contracts.specialization.IListAllSpecializationRequestsUseCase;
import com.goatspec.domain.entities.specialization.SpecializationAndUser;
import com.goatspec.infrastructure.persisntence.entities.SpecializationEntity;
import com.goatspec.mocks.Mocks;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ListAllSpecializationRequestsControllerTests {
    private final String SPEC_API = "/specs";

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @MockBean
    private IListAllSpecializationRequestsUseCase listAllSpecializationRequestsUseCase;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    @DisplayName("Should return internal server error if the usecase throws")
    void shouldReturnInternalServerErrorIfUseCaseThrows() throws Exception {
        BDDMockito.doThrow(HttpServerErrorException.InternalServerError.class)
                .when(this.listAllSpecializationRequestsUseCase).getAll();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(SPEC_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Should return list of specialization")
    void shouldReturnListOfSpecialization() throws Exception {
        List<SpecializationEntity> specializationEntityList = new ArrayList<>
                (Arrays.asList(Mocks.specializationEntityFactory(), Mocks.specializationEntityFactory()));

        List<SpecializationAndUser> specializationAndUserList = new ArrayList<>
                (Arrays.asList(Mocks.specializationAndUserFactory(specializationEntityList.getFirst()), Mocks.specializationAndUserFactory(specializationEntityList.get(1))));
        BDDMockito.when(this.listAllSpecializationRequestsUseCase.getAll()).thenReturn(specializationAndUserList);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(SPEC_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isOk());
    }
}
