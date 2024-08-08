package com.demoBank.clients.model.mapper;

import com.demoBank.clients.model.ClientRequest;
import com.demoBank.clients.model.ClientResponse;
import com.demoBank.clients.model.entity.Client;
import com.demoBank.clients.model.entity.Person;
import com.demoBank.clients.model.enums.Gender;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ClientMapper {
    @Mapping(target = "gender", source = "gender", qualifiedByName = "stringToGender")
    ClientResponse toResponse(final Client client);

    @Mapping(target = "gender", source = "gender", qualifiedByName = "genderToString")
    Client toClient(final ClientRequest client);

    @Mapping(target = "gender", source = "gender", qualifiedByName = "genderToString")
    Person toPerson(final ClientRequest client);

    @Named("stringToGender")
    default Gender stringToGender(String gender) {
        return Gender.fromCode(gender);
    }

    @Named("genderToString")
    default String genderToString(Gender gender) {
        return gender.getCode();
    }
}
