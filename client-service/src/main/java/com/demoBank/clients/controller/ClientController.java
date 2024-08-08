package com.demoBank.clients.controller;

import com.demoBank.clients.model.ApiResponse;
import com.demoBank.clients.model.ClientErrorException;
import com.demoBank.clients.model.ClientRequest;
import com.demoBank.clients.model.ClientResponse;
import com.demoBank.clients.service.ClientService;
import com.demoBank.clients.util.ApiResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clientes")
@AllArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping()
    public Flux<ClientResponse> findByIdentification(
            @RequestParam(required = false) String identification,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long id) {
        return clientService.findAllBy(identification, name, id);
    }

    @PostMapping()
    public Mono<ResponseEntity<ApiResponse<ClientResponse>>> saveClient(@RequestBody ClientRequest request) {
        return clientService.createClient(request)
                .flatMap(client -> Mono.just(new ResponseEntity<>(ApiResponseUtil.created(client), HttpStatus.CREATED)));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<ClientResponse>>> updateClient(
            @PathVariable Long id,
            @RequestBody ClientRequest request) {
        return clientService.updateClient(id, request)
                .flatMap(client -> Mono.just(ResponseEntity.ok(ApiResponseUtil.ok(client))));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<Void>>> deleteClient(@PathVariable Long id) {
        return clientService.deleteClient(id)
                .flatMap(c -> Mono.just(ResponseEntity.ok(ApiResponseUtil.delete())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception e) {
        if (e instanceof ClientErrorException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseUtil.badRequest((ClientErrorException) e));
        } else {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponseUtil.error(e));
        }
    }
}
