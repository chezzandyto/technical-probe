package com.demoBank.clients.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
    MALE("M"), FEMALE("F");

    private final String code;

    public static Gender fromCode(String code) {
        for (Gender gender : values()) {
            if (gender.getCode().equals(code)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
