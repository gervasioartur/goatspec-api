package com.goatspec.domain.Enums;

public enum GeneralEnumText {

    EMAIL_PASSWORD_EXPRESSION("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    private final String value;

    GeneralEnumText(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
