package com.demoBank.core.model;

public record ClientResponse(
        Long id,
        String name,
        String lastname,
        String identification
        ) { }
