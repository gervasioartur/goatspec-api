package com.goatspec.infrastructure.api.controllers.specialization;

import com.goatspec.application.useCases.contracts.authentication.IGetLoggedUserInfoUseCase;
import com.goatspec.application.useCases.contracts.specialization.IRemoveSpecializationRequestUseCase;
import com.goatspec.domain.entities.authentication.UserInfo;
import com.goatspec.domain.exceptions.BusinessException;
import com.goatspec.domain.exceptions.NotFoundException;
import com.goatspec.infrastructure.api.controllers.AbstractController;
import com.goatspec.infrastructure.api.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/specs")
@Tag(name = "Specialization Request", description = "Endpoints for specialization request features")
public class RemoveSpecializationRequestController extends AbstractController<String> {
    private final IRemoveSpecializationRequestUseCase removeSpecializationRequestUseCase;
    private final IGetLoggedUserInfoUseCase getLoggedUserInfoUseCase;

    public RemoveSpecializationRequestController(IRemoveSpecializationRequestUseCase removeSpecializationRequestUseCase,
                                                 IGetLoggedUserInfoUseCase getLoggedUserInfoUseCase) {
        this.removeSpecializationRequestUseCase = removeSpecializationRequestUseCase;
        this.getLoggedUserInfoUseCase = getLoggedUserInfoUseCase;
    }


    @DeleteMapping("/{specializationRequestId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Create new Specialization request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Returns successfully message"),
            @ApiResponse(responseCode = "400", description = "Bad request happened"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred"),
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Response> perform(@PathVariable(value = "specializationRequestId") String id) {
        Response response;
        ResponseEntity<Response> responseEntity;

        try {
            UserInfo userInfo = this.getLoggedUserInfoUseCase.get();
            this.removeSpecializationRequestUseCase.remove(UUID.fromString(id), userInfo.id());
            response = new Response("Specialization request successfully removed");
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        } catch (BusinessException ex) {
            response = new Response(ex.getMessage());
            responseEntity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (NotFoundException ex) {
            response = new Response(ex.getMessage());
            responseEntity = new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            response = new Response(ex.getMessage());
            responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }
}
