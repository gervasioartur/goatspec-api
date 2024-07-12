package com.goatspec.domain.Enums;

public enum GenderEnum {
    MALE("MALE"),
    FEMALE("FEMALE");

    private final String value;

    GenderEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return this.value;
    }
}
