package com.demoBank.core.repository;

import com.demoBank.core.model.entity.Transaction;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface TransactionRepository extends R2dbcRepository<Transaction, Long> {

}
