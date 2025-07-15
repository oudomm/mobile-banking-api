package com.oudom.mbapi.service.impl;

import com.oudom.mbapi.domain.Customer;
import com.oudom.mbapi.domain.KYC;
import com.oudom.mbapi.domain.Segment;
import com.oudom.mbapi.dto.CreateCustomerRequest;
import com.oudom.mbapi.dto.CustomerResponse;
import com.oudom.mbapi.dto.UpdateCustomerRequest;
import com.oudom.mbapi.mapper.CustomerMapper;
import com.oudom.mbapi.mapper.KYCMapper;
import com.oudom.mbapi.repository.CustomerRepository;
import com.oudom.mbapi.repository.KYCRepository;
import com.oudom.mbapi.repository.SegmentRepository;
import com.oudom.mbapi.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final KYCRepository kycRepository;
    private final KYCMapper kycMapper;
    private final SegmentRepository segmentRepository;

    @Override
    public List<CustomerResponse> findAll() {

        List<Customer> customers = customerRepository.findAllByIsDeletedFalse();

       return customers
               .stream()
               .map(customerMapper::fromCustomer)
               .toList();
    }


    @Override
    public CustomerResponse findByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumberAndIsDeletedFalse(phoneNumber)
                .map(customerMapper::fromCustomer)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Customer with phone number '%s' not found", phoneNumber)));
    }

    @Transactional
    @Override
    public CustomerResponse createNew(CreateCustomerRequest createCustomerRequest) {

        if (customerRepository.existsByEmail(createCustomerRequest.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists!");
        }

        if (customerRepository.existsByPhoneNumber(createCustomerRequest.phoneNumber())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number already exists!");
        }

        if (kycRepository.existsByNationalCardId(createCustomerRequest.kyc().nationalCardId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "National card ID already exists!");
        }

        Segment segment = segmentRepository.findById(createCustomerRequest.segmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Segment not found"));

        Customer customer = customerMapper.toCustomer(createCustomerRequest);
        customer.setIsDeleted(false);
        customer.setAccounts(new ArrayList<>());
        customer.setSegment(segment);

        KYC kyc = kycMapper.toKYC(createCustomerRequest.kyc());
        kyc.setIsVerified(false);
        kyc.setIsDeleted(false);
        kyc.setCustomer(customer);

        customer.setKyc(kyc);

        customerRepository.save(customer);

        return customerMapper.fromCustomer(customer);
    }

    @Override
    public CustomerResponse updateByPhoneNumber(String phoneNumber, UpdateCustomerRequest updateCustomerRequest) {

        Customer customer = customerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("Customer with phone number '%s' not found", phoneNumber)
                        ));

        customerMapper.toCustomerPartially(updateCustomerRequest, customer);

        customer = customerRepository.save(customer);

        return customerMapper.fromCustomer(customer);
    }

    @Override
    public void deleteByPhoneNumber(String phoneNumber) {

        Customer customer = customerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("Customer with phone number '%s' not found", phoneNumber)
                        ));

       customerRepository.delete(customer);
    }

    @Transactional
    @Override
    public void disableByPhoneNumber(String phoneNumber) {
        if (!customerRepository.isExitsByPhoneNumber(phoneNumber)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Phone number not found");
        }
        customerRepository.disableByPhoneNumber(phoneNumber);
    }


}
