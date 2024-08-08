package com.demoBank.clients.repository;

import com.demoBank.clients.model.entity.Client;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface ClientRepository extends R2dbcRepository<Client, Long> {
    @Query("SELECT * FROM Client c JOIN PERSON p ON c.id = p.id " +
            "WHERE (p.identification = :identification OR :identification IS NULL) " +
            "AND (p.name like :name OR :name IS NULL)" +
            "AND (c.id = :id OR :id IS NULL) ")
    Flux<Client> findByIdentification(@Param("identification") String identification,
                                      @Param("name") String name, @Param("id") Long id);
    @Query("INSERT INTO Client(id, password, status, updated_timestamp) values (:id, :password, :status, :updatedTimestamp)")
    Mono<Client> save(Long id, String password, Boolean status, LocalDateTime updatedTimestamp);

    @Query("UPDATE Client SET password = :password, status = :status, updated_timestamp = :updatedTimestamp WHERE Client.id = :id")
    Mono<Client> update(Long id, String password, Boolean status, LocalDateTime updatedTimestamp);
}
