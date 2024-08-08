package com.demoBank.core.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionReport(
        LocalDateTime date,
        String accountNumber,
        String transactionType,
        BigDecimal previousBalance,
        BigDecimal amount,
        BigDecimal finalBalance
) {
}
