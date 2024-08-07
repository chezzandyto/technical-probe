package com.demoBank.core.repository;

import com.demoBank.core.model.entity.Account;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface AccountRepository extends R2dbcRepository<Account, Long> {
    Flux<Account> findAllByClientId(Long clientId);

    @Query("SELECT to_char(trunc(random() * 1e16), 'FM0000000000000000')")
    Mono<String> generateAccountNumber();
}
