package com.demoBank.core.service;

import com.demoBank.core.model.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface TransactionService {
    Mono<TransactionResponse> createTransaction(TransactionCreateRequest request);
    Flux<TransactionReport> findAllByAccountIdBetween(Long accountId, LocalDate from, LocalDate to);
    Mono<ReportResponse> getReportByClientIdBetween(Long clientId, LocalDate from, LocalDate to);
}
