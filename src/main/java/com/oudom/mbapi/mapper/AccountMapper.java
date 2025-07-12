package com.oudom.mbapi.mapper;

import com.oudom.mbapi.domain.Account;
import com.oudom.mbapi.dto.AccountResponse;
import com.oudom.mbapi.dto.CreateAccountRequest;
import com.oudom.mbapi.dto.UpdateAccountRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(source = "customer.id", target = "custId")
    @Mapping(source = "actType.id", target = "actTypeId")
    AccountResponse fromAccount(Account account);

    Account toAccount(CreateAccountRequest createAccountRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toAccountPartially(UpdateAccountRequest updateAccountRequest, @MappingTarget Account account);
}
