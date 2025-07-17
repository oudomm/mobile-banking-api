package com.oudom.mbapi.service.impl;

import com.oudom.mbapi.domain.Account;
import com.oudom.mbapi.domain.AccountType;
import com.oudom.mbapi.domain.Customer;
import com.oudom.mbapi.dto.AccountResponse;
import com.oudom.mbapi.dto.CreateAccountRequest;
import com.oudom.mbapi.dto.UpdateAccountRequest;
import com.oudom.mbapi.mapper.AccountMapper;
import com.oudom.mbapi.repository.AccountRepository;
import com.oudom.mbapi.repository.AccountTypeRepository;
import com.oudom.mbapi.repository.CustomerRepository;
import com.oudom.mbapi.service.AccountService;
import com.oudom.mbapi.util.AccountNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final AccountMapper accountMapper;
    private final AccountNumberGenerator accountNumberGenerator;

    @Override
    public AccountResponse createAccount(CreateAccountRequest createAccountRequest) {

        Customer customer = customerRepository.findById(createAccountRequest.custId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        if (!customer.getKyc().getIsVerified()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Cannot create account: Customer KYC is not verified");
        }

        AccountType accountType = accountTypeRepository.findById(createAccountRequest.actTypeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account type not found"));

        Account account = accountMapper.toAccount(createAccountRequest);
        account.setActNo(accountNumberGenerator.generateAccountNumber());
        account.setCustomer(customer);
        account.setActType(accountType);
        account.setIsDeleted(false);
        account.setOverLimit(determineOverLimit(customer.getSegment().getName()));

        return accountMapper.fromAccount(accountRepository.save(account));
    }

    @Override
    public List<AccountResponse> findAll() {

        List<Account> accounts = accountRepository.findAllByIsDeletedFalse();

        return accounts.stream().map(accountMapper::fromAccount).toList();
    }

    @Override
    public AccountResponse findByActNo(String actNo) {
        return accountRepository.findByActNoAndIsDeletedFalse(actNo)
                .map(accountMapper::fromAccount)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("Account with account number `%s` not found", actNo)
                        ));
    }

    @Override
    public List<AccountResponse> findByCustId(Integer custId) {
        return accountRepository.findByCustomerIdAndIsDeletedFalse(custId).stream().map(accountMapper::fromAccount).toList();
    }

    @Override
    public AccountResponse updateByActNo(String actNo, UpdateAccountRequest updateAccountRequest) {

        Account account = accountRepository.findByActNoAndIsDeletedFalse(actNo)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Customer with account number '%s' not found", actNo)
                ));

        accountMapper.toAccountPartially(updateAccountRequest, account);

        account = accountRepository.save(account);

        return accountMapper.fromAccount(account);
    }

    @Override
    public void deleteByActNo(String actNo) {

        Account account = accountRepository.findByActNoAndIsDeletedFalse(actNo)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Customer with account number '%s' not found", actNo)
                ));

        accountRepository.delete(account);
    }

    @Override
    public void disableByActNo(String actNo) {

        Account account = accountRepository.findByActNoAndIsDeletedFalse(actNo)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Customer with account number '%s' not found", actNo)
                ));

        account.setIsDeleted(true);

        accountRepository.save(account);
    }

    private BigDecimal determineOverLimit(String segmentName) {
        return switch (segmentName.toLowerCase()) {
            case "gold" -> BigDecimal.valueOf(50000);
            case "silver" -> BigDecimal.valueOf(10000);
            case "regular" -> BigDecimal.valueOf(5000);
            default -> BigDecimal.valueOf(1000);
        };
    }

}
