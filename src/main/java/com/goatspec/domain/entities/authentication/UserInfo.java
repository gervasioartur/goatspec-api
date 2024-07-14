package com.goatspec.domain.entities.authentication;

import java.util.UUID;

public record UserInfo(UUID id, String name, String email, String registration) {
}
