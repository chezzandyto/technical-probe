package com.demoBank.clients.model;

import com.demoBank.clients.model.enums.Gender;

public record ClientRequest(
        Long id,
        String name,
        String lastname,
        String identification,
        Gender gender,
        Integer age,
        String address,
        String phone,
        String password,
        Boolean status
        ) { }
