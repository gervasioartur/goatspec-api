package com.goatspec.infrastructure.api.controllers.specialization;

import com.goatspec.application.useCases.contracts.specialization.IApproveSpecializationRequestUseCase;
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
@RequestMapping("/specs/approve")
@Tag(name = "Specialization Request", description = "Endpoints for specialization request features")
public class ApproveSpecializationRequestController extends AbstractController<String> {
    private final IApproveSpecializationRequestUseCase approveSpecializationRequestUseCase;

    public ApproveSpecializationRequestController(IApproveSpecializationRequestUseCase approveSpecializationRequestUseCase) {
        this.approveSpecializationRequestUseCase = approveSpecializationRequestUseCase;
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{specializationRequestId}")
    @Operation(summary = "Approve specialization request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Specialization request successfully approved"),
            @ApiResponse(responseCode = "404", description = "Specialization request not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred"),
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Response> perform(@PathVariable(value = "specializationRequestId") String id) {
        Response response;
        ResponseEntity<Response> responseEntity;

        try {
            this.approveSpecializationRequestUseCase.approve(UUID.fromString(id));
            response = new Response("Specialization request successfully approved");
            responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
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
