package com.demoBank.core.service.impl;

import com.demoBank.core.client.ClientServiceClient;
import com.demoBank.core.model.*;
import com.demoBank.core.model.entity.Transaction;
import com.demoBank.core.model.enums.TransactionType;
import com.demoBank.core.model.mapper.TransactionMapper;
import com.demoBank.core.repository.ReportViewRepository;
import com.demoBank.core.repository.TransactionRepository;
import com.demoBank.core.service.AccountService;
import com.demoBank.core.service.TransactionService;
import com.demoBank.core.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Log4j2
public class TransactionServiceImpl implements TransactionService {
    
    private final AccountService accountService;
    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    private final ClientServiceClient clientServiceClient;
    private final ReportViewRepository reportViewRepository;

    @Override
    @Transactional
    public Mono<TransactionResponse> createTransaction(TransactionCreateRequest request) {
        return accountService.findById(request.accountId())
                .doOnNext(account -> validateBalance(account.getBalance(), request.amount()))
                .map(account -> Transaction.builder()
                            .accountId(request.accountId())
                            .date(LocalDateTime.now())
                            .amount(request.amount())
                            .previousBalance(account.getBalance())
                            .finalBalance(account.getBalance().add(request.amount()))
                            .type(calculateTransactionType(request.amount()).name())
                            .build())
                .flatMap(transaction -> transactionRepository.save(transaction)
                        .flatMap(c -> accountService.updateAccount(request.accountId(), c.getFinalBalance())
                                .thenReturn(c)))
                .map(transactionMapper::toResponse);
    }

    @Override
    public Flux<TransactionReport> findAllByAccountIdBetween(Long accountId, LocalDate from, LocalDate to) {
        return reportViewRepository.getReport(accountId, DateUtil.atStartOfDay(from), DateUtil.atEndOfDay(to))
                .doOnNext(a -> System.out.println(a.getPreviousb() + " " + a.getAmount() + " " + a.getFinalb() + " " + a.getDateTime()))
                .map(a -> new TransactionReport(
                        a.getDateTime(),
                        a.getAccount(),
                        a.getTypeT(),
                        a.getPreviousb(),
                        a.getAmount(),
                        a.getFinalb()
                ));
    }

    @Override
    public Mono<ReportResponse> getReportByClientIdBetween(Long clientId, LocalDate from, LocalDate to) {
        //Fetch client name
        Mono<String> client = clientServiceClient.findById(clientId)
                .singleOrEmpty()
                .map(c -> c.name() + " " + c.lastname());
        //Get all accounts
        Flux<AccountResponse> accounts = accountService.findAllByClientId(clientId);
        //Get total balance for all accounts
        Mono<BigDecimal> totalBalance = accounts
                .map(AccountResponse::balance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        //Get transactions by account grouped
        Flux<TransactionReport> transactionPerAccount = accounts
                .flatMap(account -> findAllByAccountIdBetween(account.id(), from, to));
        //Collect and return report
        return Mono.zip(client, totalBalance, transactionPerAccount.collectList())
                .map(tuple -> {
                    String clientName = tuple.getT1();
                    BigDecimal accountsBalance = tuple.getT2();
                    var transactions = tuple.getT3();
                    Map<String, List<TransactionReport>> accountMap = transactions.stream().collect(Collectors.groupingBy(TransactionReport::accountNumber));
                    return new ReportResponse(
                            clientName,
                            accountsBalance,
                            LocalDateTime.now(),
                            accountMap
                    );
                });
    }

    private void validateBalance(final BigDecimal balance, final BigDecimal amount) {
        if (balance.add(amount).compareTo(BigDecimal.ZERO) < 0) {
            throw new TransactionException("Insufficient funds to complete the transaction");
        }
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            throw new TransactionException("Amount must not be zero");
        }
    }

    private TransactionType calculateTransactionType(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            return TransactionType.CREDIT;
        }
        return TransactionType.DEBIT;
    }
}
