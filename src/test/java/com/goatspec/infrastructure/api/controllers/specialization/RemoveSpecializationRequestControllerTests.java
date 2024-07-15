package com.goatspec.infrastructure.api.controllers.specialization;

import com.goatspec.application.useCases.contracts.authentication.IGetLoggedUserInfoUseCase;
import com.goatspec.application.useCases.contracts.specialization.IRemoveSpecializationRequestUseCase;
import com.goatspec.domain.entities.authentication.UserInfo;
import com.goatspec.domain.exceptions.BusinessException;
import com.goatspec.domain.exceptions.NotFoundException;
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

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class RemoveSpecializationRequestControllerTests {
    private final String SPEC_API = "/specs";

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @MockBean
    private IRemoveSpecializationRequestUseCase removeSpecializationRequestUseCase;
    @MockBean
    private IGetLoggedUserInfoUseCase getLoggedUserInfoUseCase;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    @DisplayName("Should return not found if specialization request does not exist")
    void shouldReturnNotIfSpecializationRequestExist() throws Exception {
        UUID specializationRequestId = UUID.randomUUID();
        UserInfo userInfoDomainObject = new UserInfo(UUID.randomUUID(), "any_name", "any_email", "any_registration");

        BDDMockito.when(this.getLoggedUserInfoUseCase.get()).thenReturn(userInfoDomainObject);
        BDDMockito.doThrow(new NotFoundException("Specialization request not found."))
                .when(this.removeSpecializationRequestUseCase).remove(specializationRequestId, userInfoDomainObject.id());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(SPEC_API + "/" + specializationRequestId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);


        mvc
                .perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("body", Matchers.is("Specialization request not found.")));
    }

    @Test
    @DisplayName("Should return Unauthorized if the current user is not owner of specialization request")
    void shouldUnauthorizedExceptionIfTheCurrentUserIsNotTheOwner() throws Exception {
        UUID specializationRequestId = UUID.randomUUID();
        UserInfo userInfoDomainObject = new UserInfo(UUID.randomUUID(), "any_name", "any_email", "any_registration");
        BDDMockito.when(this.getLoggedUserInfoUseCase.get()).thenReturn(userInfoDomainObject);
        BDDMockito.doThrow(new BusinessException("You can only remove specialization request on pending status."))
                .when(this.removeSpecializationRequestUseCase).remove(specializationRequestId, userInfoDomainObject.id());


        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(SPEC_API + "/" + specializationRequestId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("body", Matchers.is("You can only remove specialization request on pending status.")));
    }

    @Test
    @DisplayName("Should return success message")
    void shouldReturnSuccessMessage() throws Exception {
        UUID specializationRequestId = UUID.randomUUID();
        UserInfo userInfoDomainObject = new UserInfo(UUID.randomUUID(), "any_name", "any_email", "any_registration");
        BDDMockito.when(this.getLoggedUserInfoUseCase.get()).thenReturn(userInfoDomainObject);
        BDDMockito.doNothing().when(this.removeSpecializationRequestUseCase).remove(specializationRequestId, userInfoDomainObject.id());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(SPEC_API + "/" + specializationRequestId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc
                .perform(requestBuilder)
                .andExpect(status().isOk());

    }
}
