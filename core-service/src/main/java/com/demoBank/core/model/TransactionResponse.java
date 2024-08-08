package com.demoBank.core.model;

import com.demoBank.core.model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        Long id,
        String accountId,
        LocalDateTime date,
        BigDecimal amount,
        BigDecimal previousBalance,
        BigDecimal finalBalance,
        TransactionType type
        ) { }
