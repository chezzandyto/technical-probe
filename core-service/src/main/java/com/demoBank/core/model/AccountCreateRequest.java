package com.demoBank.core.model;

import com.demoBank.core.model.enums.AccountType;

public record AccountCreateRequest(
        Long clientId,
        AccountType type
        ) { }
