package com.demoBank.clients.repository;

import com.demoBank.clients.model.entity.Client;
import com.demoBank.clients.model.entity.Person;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface PersonRepository extends R2dbcRepository<Person, Long> {

}
