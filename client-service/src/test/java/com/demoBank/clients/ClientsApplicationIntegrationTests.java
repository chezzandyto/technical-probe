package com.demoBank.clients;

import com.demoBank.clients.model.ClientResponse;
import com.demoBank.clients.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataR2dbcTest
@ComponentScan(basePackages = "com.demoBank.clients")
@ActiveProfiles("test")
public class ClientsApplicationIntegrationTests {
	@Autowired private ClientService clientService;
	@Autowired private DatabaseClient databaseClient;

	@BeforeEach
	void setUp() {
		executeScript("src/test/resources/sql/schema.sql").block();
		executeScript("src/test/resources/sql/inserts.sql").block();
	}

	private Mono<Void> executeScript(String scriptPath) {
		return databaseClient.sql(ResourceUtils.readFileToString(scriptPath)).then();
	}

	@Test
	void createClient_shouldReturnTheOneClientPreviouslyCreated() {
		Flux<ClientResponse> responseFlux = clientService.findAllBy("0145268973", null, null);

		StepVerifier.create(responseFlux)
				.assertNext(clientResponse -> {
					assertNotNull(clientResponse);
					assertEquals("JOSE", clientResponse.name());
				})
				.verifyComplete();
	}

}
