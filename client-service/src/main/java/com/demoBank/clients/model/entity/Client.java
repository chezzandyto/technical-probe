package com.demoBank.clients.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "CLIENT")
public class Client extends Person {
    private String password;
    @Column("status")
    protected Boolean status;
    @Column("updated_timestamp")
    protected LocalDateTime updatedTimestamp;
}
