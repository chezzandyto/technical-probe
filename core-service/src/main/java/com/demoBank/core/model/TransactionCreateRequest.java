package com.demoBank.core.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record TransactionCreateRequest(
        Long accountId,
        BigDecimal amount
        ) {
        public TransactionCreateRequest(Long accountId, BigDecimal amount) {
                this.amount = amount.setScale(2, RoundingMode.HALF_UP);
                this.accountId = accountId;
        }
}
