package com.goatspec.domain.Enums;

public enum SpecializationTypeEnum {
    POS_GRADUCAO("POS GRADUAÇÃO"),
    MESTRADO("MESTRADO"),
    DOUTORADO("DOUTORADO"),
    POSTGRADUATE("POSTGRADUATE"),
    MASTER_DEGREE("MASTER'S DEGREE"),
    DOCTORATE_DEGREE("DOCTORATE DEGREE");

    private final String value;

    SpecializationTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
