package com.demoBank.core.model.entity;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Table(name = "MOVREPORT")
public class ReportView {
    private BigDecimal previousb;
    @Column("datetime")
    private LocalDateTime dateTime;
    private BigDecimal amount;
    private Long id;
    private String account;
    @Column("typeT")
    private String typeT;
    private BigDecimal finalb;
}
