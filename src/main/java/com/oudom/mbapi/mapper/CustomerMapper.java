package com.oudom.mbapi.mapper;

import com.oudom.mbapi.domain.Customer;
import com.oudom.mbapi.dto.CreateCustomerRequest;
import com.oudom.mbapi.dto.CustomerResponse;
import com.oudom.mbapi.dto.UpdateCustomerRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    // DTO -> Domain Model
    // Domain Model -> DTO
    // return type is converted | target data
    // parameter is source data

    CustomerResponse fromCustomer(Customer customer);

    Customer toCustomer(CreateCustomerRequest createCustomerRequest);

    // Partially update
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toCustomerPartially(UpdateCustomerRequest updateCustomerRequest, @MappingTarget Customer customer);
}
