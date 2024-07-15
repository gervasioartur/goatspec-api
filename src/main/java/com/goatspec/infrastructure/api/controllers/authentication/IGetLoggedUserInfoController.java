package com.goatspec.infrastructure.api.controllers.authentication;

import com.goatspec.application.useCases.contracts.authentication.IGetLoggedUserInfoUseCase;
import com.goatspec.domain.entities.authentication.UserInfo;
import com.goatspec.infrastructure.api.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/user-info")
@Tag(name = "Authentication", description = "Endpoints for authentication features")
public class IGetLoggedUserInfoController {
    private final IGetLoggedUserInfoUseCase getLoggedUserInfoUseCase;

    public IGetLoggedUserInfoController(IGetLoggedUserInfoUseCase getLoggedUserInfoUseCase) {
        this.getLoggedUserInfoUseCase = getLoggedUserInfoUseCase;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "List all specialization requests")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns all specialization requests"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred"),
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Response> perform() {
        Response response;
        ResponseEntity<Response> responseEntity;

        try {
            UserInfo userInfo = this.getLoggedUserInfoUseCase.get();
            response = new Response(userInfo);
            responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response = new Response(ex.getMessage());
            responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
