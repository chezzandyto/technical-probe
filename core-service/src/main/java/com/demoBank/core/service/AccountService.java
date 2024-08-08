package com.demoBank.core.service;

import com.demoBank.core.model.AccountCreateRequest;
import com.demoBank.core.model.AccountResponse;
import com.demoBank.core.model.AccountUpdateRequest;
import com.demoBank.core.model.entity.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface AccountService {
    Flux<AccountResponse> findAllByClientIdAndStatus(Long clientId, Boolean status);
    Mono<AccountResponse> createAccount(AccountCreateRequest request);
    Mono<AccountResponse> updateAccount(Long id, AccountUpdateRequest request);
    Mono<Void> updateAccount(Long id, BigDecimal amount);
    Mono<Void> deleteAccount(Long id);
    Mono<Account> findById(Long id);
}
