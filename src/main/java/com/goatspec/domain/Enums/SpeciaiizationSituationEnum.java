package com.goatspec.domain.Enums;

public enum SpeciaiizationSituationEnum {
    PENDING("PENDING"),
    APPROVED("APPROVED"),
    DISAPPROVED("DISAPPROVED");

    private final String value;

    SpeciaiizationSituationEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
