package com.demoBank.clients.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Data
@Table(name = "PERSON")
public class Person {
    @Id
    private Long id;
    private String name;
    private String lastname;
    private String identification;
    private String gender;
    private Integer age;
    private String address;
    private String phone;
}
