package com.goatspec.domain.entities;

import java.util.Date;

public record User(String cpf, String email, String registration, String name, Date dateOfBirth, String gender,String type) {
}
