package com.demoBank.core.model.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Table(name = "ACCOUNT")
@Builder
public class Account {
    @Id
    private Long id;
    @Column("account_number")
    private String accountNumber;
    @Column("client_id")
    private Long clientId;
    private String type;
    private BigDecimal balance;
    protected Boolean status;
    @Column("updated_timestamp")
    protected LocalDateTime updatedTimestamp;
}
