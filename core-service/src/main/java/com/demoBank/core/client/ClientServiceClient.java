package com.demoBank.core.client;

import com.demoBank.core.model.ClientErrorException;
import com.demoBank.core.model.ClientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClientServiceClient {
    private final WebClient webClient;

    @Autowired
    public ClientServiceClient(WebClient.Builder webClientBuilder, @Value("${api.client.url}") String url) {
        this.webClient = webClientBuilder.baseUrl(url).build();
    }

    public Flux<ClientResponse> findById(Long id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/clientes")
                        .queryParam("id", id)
                        .build())
                .retrieve()
                .bodyToFlux(ClientResponse.class)
                .switchIfEmpty(Mono.error(new ClientErrorException("Client not found with id: " + id)))
                .onErrorMap(w -> new ClientErrorException(w.getMessage()));
    }
}
