package com.goatspec.domain.Enums;

public enum SpecializationRequestStatusEnum {
    PENDING("PENDING"),
    APPROVED("APPROVED"),
    DISAPPROVED("DISAPPROVED");

    private final String value;

    SpecializationRequestStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
