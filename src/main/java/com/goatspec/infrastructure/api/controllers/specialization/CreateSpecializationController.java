package com.goatspec.infrastructure.api.controllers.specialization;

import com.goatspec.application.useCases.contracts.authentication.IGetLoggedUserInfoUseCase;
import com.goatspec.application.useCases.contracts.specialization.ICreateSpecializationUseCase;
import com.goatspec.domain.entities.authentication.UserInfo;
import com.goatspec.domain.entities.specialization.Specialization;
import com.goatspec.domain.exceptions.UnauthorizedException;
import com.goatspec.infrastructure.api.controllers.AbstractController;
import com.goatspec.infrastructure.api.dto.CreateSpecializationRequest;
import com.goatspec.infrastructure.api.dto.Response;
import com.goatspec.infrastructure.api.validation.ValidationBuilder;
import com.goatspec.infrastructure.api.validation.validators.contract.IValidator;
import com.goatspec.infrastructure.gateways.mappers.SpecializationDTOMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/specs")
@Tag(name = "Specialization", description = "Endpoints for specialization features")
public class CreateSpecializationController extends AbstractController<CreateSpecializationRequest> {
    private final ICreateSpecializationUseCase specializationUseCase;
    private final SpecializationDTOMapper specializationDTOMapper;
    private final IGetLoggedUserInfoUseCase getLoggedUserInfoUseCase;

    public CreateSpecializationController(ICreateSpecializationUseCase specializationUseCase,
                                          SpecializationDTOMapper specializationDTOMapper,
                                          IGetLoggedUserInfoUseCase getLoggedUserInfoUseCase) {
        this.specializationUseCase = specializationUseCase;
        this.specializationDTOMapper = specializationDTOMapper;
        this.getLoggedUserInfoUseCase = getLoggedUserInfoUseCase;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new Specialization request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Returns successfully message"),
            @ApiResponse(responseCode = "400", description = "Bad request happened"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred"),
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Response> perform(@RequestBody CreateSpecializationRequest request) {
        Response response;
        ResponseEntity<Response> responseEntity;

        String error = this.validate(request);
        if (error != null) {
            response = new Response(error);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            UserInfo userInfo =  this.getLoggedUserInfoUseCase.get();
            Specialization specializationDomainObject = this.specializationDTOMapper.toDomainObject(request, userInfo.id());
            this.specializationUseCase.create(specializationDomainObject);
            response = new Response("Successfully created specialization");
            responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
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
    public List<IValidator> buildValidators(CreateSpecializationRequest request) {
        List<IValidator> validators = new ArrayList<>();
        validators.addAll(ValidationBuilder.of("area", request.area()).required().build());
        validators.addAll(ValidationBuilder.of("type", request.type()).required().specType().build());
        validators.addAll(ValidationBuilder.of("course load", request.courseLoad()).min(60).build());

        return validators;
    }
}
