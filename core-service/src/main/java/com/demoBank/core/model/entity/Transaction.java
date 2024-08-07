package com.demoBank.core.model.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Table(name = "TRANSACTION")
@Builder
public class Transaction {
    @Id
    private Long id;
    private Long accountId;
    private LocalDateTime date;
    private BigDecimal amount;
    private BigDecimal previousBalance;
    private BigDecimal finalBalance;
    private String type;
}
