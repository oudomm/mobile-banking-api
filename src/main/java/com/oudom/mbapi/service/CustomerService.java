package com.oudom.mbapi.service;

import com.oudom.mbapi.dto.CreateCustomerRequest;
import com.oudom.mbapi.dto.CustomerResponse;
import com.oudom.mbapi.dto.UpdateCustomerRequest;

import java.util.List;

public interface CustomerService {

    List<CustomerResponse> findAll();

    CustomerResponse findByPhoneNumber(String phoneNumber);

    CustomerResponse createNew(CreateCustomerRequest createCustomerRequest);

    CustomerResponse updateByPhoneNumber(String phoneNumber, UpdateCustomerRequest updateCustomerRequest);

    void deleteByPhoneNumber(String phoneNumber);

    void disableByPhoneNumber(String phoneNumber);
}
