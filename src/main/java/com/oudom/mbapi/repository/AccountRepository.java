package com.oudom.mbapi.repository;

import com.oudom.mbapi.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    List<Account> findByIsDeletedFalse();

    Optional<Account> findByActNoAndIsDeletedFalse(String actNo);

    List<Account> findByCustomerIdAndIsDeletedFalse(Integer custId);

    boolean existsByActNo(String actNo);
}
