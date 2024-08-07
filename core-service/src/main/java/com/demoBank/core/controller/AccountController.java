package com.demoBank.core.controller;

import com.demoBank.core.model.*;
import com.demoBank.core.service.AccountService;
import com.demoBank.core.util.ApiResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cuentas")
@AllArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/cliente/{clientId}")
    public Flux<AccountResponse> findByClientId(@PathVariable Long clientId) {
        return accountService.findAllByClientId(clientId);
    }

    @PostMapping()
    public Mono<ResponseEntity<ApiResponse<AccountResponse>>> saveAccount(@RequestBody AccountCreateRequest request) {
        return accountService.createAccount(request)
                .flatMap(account -> Mono.just(new ResponseEntity<>(ApiResponseUtil.created(account), HttpStatus.CREATED)));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<AccountResponse>>> updateClient(
            @PathVariable Long id,
            @RequestBody AccountUpdateRequest request) {
        return accountService.updateAccount(id, request)
                .flatMap(client -> Mono.just(ResponseEntity.ok(ApiResponseUtil.ok(client))));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<Void>>> deleteClient(@PathVariable Long id) {
        return accountService.deleteAccount(id)
                .flatMap(c -> Mono.just(ResponseEntity.ok(ApiResponseUtil.delete())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception e) {
        if (e instanceof ClientErrorException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseUtil.badRequest((RuntimeException) e));
        } else {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponseUtil.error(e));
        }
    }
}
