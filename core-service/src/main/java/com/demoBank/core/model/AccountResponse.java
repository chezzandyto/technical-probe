package com.demoBank.core.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponse(
        Long id,
        String accountNumber,
        Long clientId,
        String type,
        BigDecimal balance,
        Boolean status,
        LocalDateTime updatedTimestamp
        ) { }
