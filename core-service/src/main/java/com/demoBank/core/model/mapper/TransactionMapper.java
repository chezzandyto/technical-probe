package com.demoBank.core.model.mapper;

import com.demoBank.core.model.TransactionResponse;
import com.demoBank.core.model.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(target = "type", source = "type", qualifiedByName = "stringToType")
    TransactionResponse toResponse(final Transaction transaction);

}
