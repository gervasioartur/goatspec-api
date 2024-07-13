package com.goatspec.infrastructure.api.controllers;

import com.goatspec.application.useCases.contracts.ICreateUserUseCase;
import com.goatspec.domain.entities.user.User;
import com.goatspec.domain.exceptions.BusinessException;
import com.goatspec.domain.exceptions.UnexpectedException;
import com.goatspec.infrastructure.api.dto.CreateUserRequest;
import com.goatspec.infrastructure.api.dto.Response;
import com.goatspec.infrastructure.api.validation.ValidationBuilder;
import com.goatspec.infrastructure.api.validation.validators.contract.IValidator;
import com.goatspec.infrastructure.gateways.mappers.UserDTOMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class CreateUserController extends AbstractController<CreateUserRequest> {
    private final ICreateUserUseCase createUserUseCase;
    private final UserDTOMapper userDTOMapper;

    public CreateUserController(ICreateUserUseCase createUserUseCase, UserDTOMapper userDTOMapper) {
        this.createUserUseCase = createUserUseCase;
        this.userDTOMapper = userDTOMapper;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Response> perform(@RequestBody CreateUserRequest request) {
        Response response = null;
        ResponseEntity<Response> responseEntity = null;

        String error = this.validate(request);
        if (error != null) {
            response = new Response(error);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            User userDomainObject = this.userDTOMapper.toUserDomainObject(request);
            this.createUserUseCase.create(userDomainObject);
        }catch (BusinessException | UnexpectedException ex ){
            response = new Response(ex.getMessage());
            responseEntity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
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
        validators.addAll(ValidationBuilder.of("role", request.role()).required().role().build());
        validators.addAll(ValidationBuilder.of("password", request.password()).required().password().build());
        return validators;
    }
}
