package com.goatspec.infrastructure.api.controllers;

import com.goatspec.application.useCases.contracts.ICreateUserUseCase;
import com.goatspec.infrastructure.api.dto.CreateUserRequest;
import com.goatspec.infrastructure.api.dto.Response;
import com.goatspec.infrastructure.api.validation.ValidationBuilder;
import com.goatspec.infrastructure.api.validation.validators.contract.IValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class CreateUserController extends AbstractController<CreateUserRequest> {
    private final ICreateUserUseCase createUserUseCase;

    public CreateUserController(ICreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Response> perform(CreateUserRequest request) {
        Response response = null;

        String error = this.validate(request);
        if (error != null) {
            response = new Response(error);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return null;
    }

    @Override
    public List<IValidator> buildValidators(CreateUserRequest request) {
        List<IValidator> validators = new ArrayList<>();
        validators.addAll(ValidationBuilder.of("CPF", request.cpf()).required().build());
        return validators;
    }
}
