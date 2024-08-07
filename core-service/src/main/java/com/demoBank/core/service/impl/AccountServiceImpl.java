package com.demoBank.core.service.impl;

import com.demoBank.core.client.ClientServiceClient;
import com.demoBank.core.model.AccountUpdateRequest;
import com.demoBank.core.model.ClientErrorException;
import com.demoBank.core.model.AccountCreateRequest;
import com.demoBank.core.model.AccountResponse;
import com.demoBank.core.model.entity.Account;
import com.demoBank.core.model.mapper.AccountMapper;
import com.demoBank.core.repository.AccountRepository;
import com.demoBank.core.repository.TransactionRepository;
import com.demoBank.core.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Service
@Log4j2
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ClientServiceClient clientServiceClient;
    private final AccountMapper accountMapper;

    @Override
    public Flux<AccountResponse> findAllByClientId(Long clientId) {
        return accountRepository.findAllByClientId(clientId)
                .map(accountMapper::toResponse);
    }

    @Override
    @Transactional
    public Mono<AccountResponse> createAccount(AccountCreateRequest request) {
        return clientServiceClient.findById(request.clientId())
                .singleOrEmpty()
                .flatMap(r -> accountRepository.generateAccountNumber())
                .map(accountNumber -> Account.builder()
                            .accountNumber(accountNumber)
                            .clientId(request.clientId())
                            .type(request.type().name())
                            .balance(BigDecimal.ZERO)
                            .status(true)
                            .updatedTimestamp(LocalDateTime.now())
                            .build())
                .flatMap(accountRepository::save)
                .map(accountMapper::toResponse);
    }

    @Override
    @Transactional
    public Mono<AccountResponse> updateAccount(Long id, AccountUpdateRequest request) {
        return findById(id)
                .map(account -> {
                    account.setStatus(request.status());
                    return account;
                })
                .flatMap(accountRepository::save)
                .map(accountMapper::toResponse);
    }

    @Override
    public Mono<Void> updateAccount(Long id, BigDecimal newBalance) {
        return findById(id)
                .map(account -> {
                    account.setBalance(newBalance);
                    account.setUpdatedTimestamp(LocalDateTime.now());
                    return account;
                })
                .flatMap(accountRepository::save).then();
    }

    @Override
    @Transactional
    public Mono<Void> deleteAccount(Long id) {
        return findById(id)
                .flatMap(c -> accountRepository.deleteById(id));
    }

    @Override
    public Mono<Account> findById(Long id) {
        return accountRepository.findById(id)
                .switchIfEmpty(
                        Mono.error(
                        new ClientErrorException("Account with id: " + id + " not exists")));
    }
}
