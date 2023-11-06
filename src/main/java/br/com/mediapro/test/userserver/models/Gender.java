package br.com.mediapro.test.userserver.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    MALE,
    FEMALE;

    @JsonValue
    public String jsonValue() {
        return name().toLowerCase();
    }
}
