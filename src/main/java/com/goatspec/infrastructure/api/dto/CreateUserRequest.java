package com.goatspec.infrastructure.api.dto;

import java.util.Date;

public record CreateUserRequest(String cpf, String email, String registration, String name, Date dateOfBirth,
                                String gender,
                                String role, String password) {
}