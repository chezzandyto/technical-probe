package com.demoBank.clients.service.impl;

import com.demoBank.clients.model.ClientErrorException;
import com.demoBank.clients.model.ClientRequest;
import com.demoBank.clients.model.ClientResponse;
import com.demoBank.clients.model.entity.Client;
import com.demoBank.clients.model.mapper.ClientMapper;
import com.demoBank.clients.repository.ClientRepository;
import com.demoBank.clients.repository.PersonRepository;
import com.demoBank.clients.service.ClientService;
import com.demoBank.clients.util.HashUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
@Log4j2
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final PersonRepository personRepository;

    @Override
    public Flux<ClientResponse> findAllBy(String identification, String name, Long id) {
        return clientRepository.findByIdentification(identification, name, id)
                .map(clientMapper::toResponse);
    }

    @Override
    @Transactional
    public Mono<ClientResponse> createClient(ClientRequest request) {
        return Mono.just(request)
                .map(clientMapper::toPerson)
                .flatMap(personRepository::save)
                .onErrorMap(DuplicateKeyException.class, e -> {
                    log.error(e.getMessage());
                    return new ClientErrorException("Client with identification: " + request.identification() + " already exists");
                })
                .flatMap(person -> {
                    var s = clientMapper.toClient(request);
                    return clientRepository.save(
                            person.getId(),
                            HashUtil.hashPassword(s.getPassword()),
                            true,
                            LocalDateTime.now()
                    );
                })
                .map(clientMapper::toResponse);
    }

    @Override
    @Transactional
    public Mono<ClientResponse> updateClient(Long clientId, ClientRequest request) {
        return findById(clientId)
                .map(c -> request)
                .map(clientMapper::toPerson)
                .doOnNext(person -> person.setId(clientId))
                .flatMap(personRepository::save)
                .flatMap(cr -> {
                    var s = clientMapper.toClient(request);
                    return clientRepository.update(
                            s.getId(),
                            HashUtil.hashPassword(s.getPassword()),
                            s.getStatus(),
                            LocalDateTime.now());
                })
                .map(clientMapper::toResponse);
    }

    @Override
    @Transactional
    public Mono<Void> deleteClient(Long id) {
        return findById(id)
                .flatMap(c -> clientRepository.deleteById(id))
                .then(personRepository.deleteById(id));
    }

    private Mono<Client> findById(Long id) {
        return clientRepository.findByIdentification(null, null, id)
                .switchIfEmpty(
                        Mono.error(
                        new ClientErrorException("Client with id: " + id + " not exists")))
                .singleOrEmpty();
    }
}
