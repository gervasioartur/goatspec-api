package com.goatspec.domain.Enums;

public enum RoleEnum {
    TECHNICIAN("ROLE_TECHNICIAN"),
    TEACHER("ROLE_TEACHER");

    private final String value;

    RoleEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
