package com.goatspec.domain.entities.email;

public record SendEmailParams(String receiver, String subject, Object body) {
}
