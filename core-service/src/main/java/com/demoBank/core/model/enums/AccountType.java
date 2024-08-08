package com.demoBank.core.model.enums;

import com.demoBank.core.model.ClientErrorException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonParseException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum AccountType {
    SAVINGS, CHECKING;

    @JsonCreator
    public static AccountType fromValue(String value) throws JsonParseException {
        for (AccountType type : AccountType.values()) {
            if (type.name().equals(value)) {
                return type;
            }
        }
        throw new JsonParseException("AccountType not valid. Allowed: " + Arrays.toString(AccountType.values()));
    }
}
