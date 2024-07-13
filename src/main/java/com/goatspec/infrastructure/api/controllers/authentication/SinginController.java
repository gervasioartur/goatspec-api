package com.goatspec.infrastructure.api.controllers.authentication;

import com.goatspec.application.useCases.contracts.authentication.ISinginUseCase;
import com.goatspec.domain.entities.user.User;
import com.goatspec.domain.entities.user.UserAccount;
import com.goatspec.domain.exceptions.BusinessException;
import com.goatspec.domain.exceptions.UnauthorizedException;
import com.goatspec.domain.exceptions.UnexpectedException;
import com.goatspec.infrastructure.api.controllers.AbstractController;
import com.goatspec.infrastructure.api.dto.Response;
import com.goatspec.infrastructure.api.dto.SinginRequest;
import com.goatspec.infrastructure.api.validation.ValidationBuilder;
import com.goatspec.infrastructure.api.validation.validators.contract.IValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth/singin")
@Tag(name = "Authentication", description = "Endpoints for authentication features")
public class SinginController extends AbstractController<SinginRequest> {
    private final ISinginUseCase singinUseCase;

    public SinginController(ISinginUseCase singinUseCase) {
        this.singinUseCase = singinUseCase;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Sing in")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the user token"),
            @ApiResponse(responseCode = "400", description = "Bad request happened"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred"),
    })
    public ResponseEntity<Response> perform(@RequestBody SinginRequest request) {
        Response response;
        ResponseEntity<Response> responseEntity;

        String error = this.validate(request);
        if (error != null) {
            response = new Response(error);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            UserAccount userAccount = this.singinUseCase.singin(request.cpf(),request.password());
            response = new Response(userAccount);
            responseEntity = new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (UnauthorizedException ex) {
            response = new Response(ex.getMessage());
            responseEntity = new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            response = new Response(ex.getMessage());
            responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @Override
    public List<IValidator> buildValidators(SinginRequest request) {
        List<IValidator> validators = new ArrayList<>();
        validators.addAll(ValidationBuilder.of("CPF", request.cpf()).required().build());
        validators.addAll(ValidationBuilder.of("password", request.password()).required().build());
        return validators;
    }
}
