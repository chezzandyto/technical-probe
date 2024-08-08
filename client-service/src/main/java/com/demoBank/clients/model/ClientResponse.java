package com.demoBank.clients.model;

import com.demoBank.clients.model.enums.Gender;

import java.io.Serializable;

public record ClientResponse (
        Long id,
        String name,
        String lastname,
        String identification,
        Gender gender,
        Integer age,
        String address,
        String phone
        ) implements Serializable { }
