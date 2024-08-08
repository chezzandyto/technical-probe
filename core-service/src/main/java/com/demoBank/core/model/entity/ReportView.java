package com.demoBank.core.model.entity;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Table(name = "MOVREPORT")
public class ReportView {
    private BigDecimal previousb;
    private LocalDateTime dateTime;
    private BigDecimal amount;
    private Long id;
    private String account;
    private String typeT;
    private BigDecimal finalb;
}
