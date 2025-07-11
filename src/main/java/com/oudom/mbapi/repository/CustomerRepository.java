package com.oudom.mbapi.repository;

import com.oudom.mbapi.domain.Customer;
import com.oudom.mbapi.dto.CustomerResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    // SELECT * FROM customers WHERE phone_number = phoneNumber;
    Optional<Customer> findByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
}
