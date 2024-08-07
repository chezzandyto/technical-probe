package com.demoBank.core.model.mapper;

import com.demoBank.core.model.AccountCreateRequest;
import com.demoBank.core.model.AccountResponse;
import com.demoBank.core.model.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountResponse toResponse(final Account account);

}
