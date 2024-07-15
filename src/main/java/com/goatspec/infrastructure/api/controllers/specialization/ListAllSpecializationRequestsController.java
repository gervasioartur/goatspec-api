package com.goatspec.infrastructure.api.controllers.specialization;

import com.goatspec.application.useCases.contracts.specialization.IListAllSpecializationRequestsUseCase;
import com.goatspec.infrastructure.api.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/specs")
@Tag(name = "Specialization Request", description = "Endpoints for specialization request features")
public class ListAllSpecializationRequestsController {

    private final IListAllSpecializationRequestsUseCase listAllSpecializationRequestsUseCase;

    public ListAllSpecializationRequestsController(IListAllSpecializationRequestsUseCase listAllSpecializationRequestsUseCase) {
        this.listAllSpecializationRequestsUseCase = listAllSpecializationRequestsUseCase;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "List all specialization requests")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns all specialization requests"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred"),
    })
    public ResponseEntity<Response> perform() {
        Response response;
        ResponseEntity<Response> responseEntity;

        try {
            response = new Response(this.listAllSpecializationRequestsUseCase.listAll());
            responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response = new Response(ex.getMessage());
            responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }
}