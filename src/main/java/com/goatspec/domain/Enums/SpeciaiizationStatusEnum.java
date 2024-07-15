package com.goatspec.domain.Enums;

public enum SpeciaiizationStatusEnum {
    PENDING("PENDING"),
    APPROVED("APPROVED"),
    DISAPPROVED("DISAPPROVED");

    private final String value;

    SpeciaiizationStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
