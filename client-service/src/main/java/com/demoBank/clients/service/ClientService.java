package com.demoBank.clients.service;

import com.demoBank.clients.model.ClientRequest;
import com.demoBank.clients.model.ClientResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {
    Flux<ClientResponse> findAllBy(String identification, String name, Long id);
    Mono<Void> deleteClient(Long id);
    Mono<ClientResponse> createClient(ClientRequest request);
    Mono<ClientResponse> updateClient(Long clientId, ClientRequest request);
}
