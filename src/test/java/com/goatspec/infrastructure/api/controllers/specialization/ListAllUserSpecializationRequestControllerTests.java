package com.goatspec.infrastructure.api.controllers.specialization;

import com.goatspec.application.useCases.contracts.authentication.IGetLoggedUserInfoUseCase;
import com.goatspec.application.useCases.contracts.specialization.IListAllUserSpecializationRequestUseCase;
import com.goatspec.domain.entities.authentication.UserInfo;
import com.goatspec.domain.entities.specialization.SpecializationRequestInfo;
import com.goatspec.infrastructure.persisntence.entities.SpecializationRequestEntity;
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
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ListAllUserSpecializationRequestControllerTests {
    private final String SPEC_API = "/specs";

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @MockBean
    private IListAllUserSpecializationRequestUseCase listAllUserSpecializationRequestUseCase;

    @MockBean
    private IGetLoggedUserInfoUseCase getLoggedUserInfoUseCase;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    @DisplayName("Should return internal server error if the usecase throws")
    void shouldReturnInternalServerErrorIfUseCaseThrows() throws Exception {
        UUID userId = UUID.randomUUID();


        BDDMockito.doThrow(HttpServerErrorException.InternalServerError.class)
                .when(this.listAllUserSpecializationRequestUseCase).list(userId);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(SPEC_API + "/user")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Should return list of specialization")
    void shouldReturnListOfSpecialization() throws Exception {
        List<SpecializationRequestEntity> specializationRequestEntityList = new ArrayList<>
                (Arrays.asList(Mocks.specializationEntityFactory(), Mocks.specializationEntityFactory()));

        List<SpecializationRequestInfo> specializationRequestInfoList = Mocks.specializationInfoListFactory(specializationRequestEntityList);

        UserInfo userInfo = Mocks.userInfoFactory();

        BDDMockito.when(this.getLoggedUserInfoUseCase.get()).thenReturn(userInfo);

        BDDMockito.when(this.listAllUserSpecializationRequestUseCase.list(userInfo.id()))
                .thenReturn(specializationRequestInfoList);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(SPEC_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isOk());
    }

}
