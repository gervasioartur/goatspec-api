package com.goatspec.infrastructure.api.controllers;

import com.goatspec.application.useCases.contracts.ICreateUserUseCase;
import com.goatspec.infrastructure.api.dto.CreateUserRequest;
import com.goatspec.infrastructure.api.dto.Response;
import com.goatspec.infrastructure.api.validation.ValidationBuilder;
import com.goatspec.infrastructure.api.validation.validators.contract.IValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Response> perform(@RequestBody CreateUserRequest request) {
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
        validators.addAll(ValidationBuilder.of("CPF", request.cpf()).required().cpf().build());
        validators.addAll(ValidationBuilder.of("E-mail", request.email()).required().email().build());
        validators.addAll(ValidationBuilder.of("registration", request.registration()).required().build());
        validators.addAll(ValidationBuilder.of("name", request.name()).required().build());
        validators.addAll(ValidationBuilder.of("date of birth", request.dateOfBirth()).required().build());
        validators.addAll(ValidationBuilder.of("gender", request.gender()).required().build());

        return validators;
    }
}
