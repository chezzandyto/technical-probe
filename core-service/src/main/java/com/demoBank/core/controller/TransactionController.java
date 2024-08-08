package com.demoBank.core.controller;

import com.demoBank.core.model.*;
import com.demoBank.core.service.TransactionService;
import com.demoBank.core.util.ApiResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/movimientos")
@AllArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping()
    public Mono<ResponseEntity<ApiResponse<TransactionResponse>>> newTransaction(@RequestBody TransactionCreateRequest request) {
        return transactionService.createTransaction(request)
                .flatMap(account -> Mono.just(new ResponseEntity<>(ApiResponseUtil.created(account), HttpStatus.CREATED)));
    }

    @GetMapping("/account/{accountId}")
    public Flux<TransactionReport> getReportByAccount(
            @PathVariable Long accountId,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate from,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate to) {
        return transactionService.findAllByAccountIdBetween(accountId, from, to);
    }

    @GetMapping("/client/{clientId}")
    public Mono<ReportResponse> getReportByClient(
            @PathVariable Long clientId,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate from,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate to) {
        return transactionService.getReportByClientIdBetween(clientId, from, to);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception e) {
        if (e instanceof ClientErrorException || e instanceof TransactionException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseUtil.badRequest((RuntimeException) e));
        } else {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponseUtil.error(e));
        }
    }
}
