package com.oudom.mbapi.service;

import com.oudom.mbapi.dto.AccountResponse;
import com.oudom.mbapi.dto.CreateAccountRequest;
import com.oudom.mbapi.dto.UpdateAccountRequest;

import java.util.List;

public interface AccountService {

    AccountResponse createAccount(CreateAccountRequest createAccountRequest);

    List<AccountResponse> findAll();

    AccountResponse findByActNo(String actNo);

    List<AccountResponse> findByCustId(Integer custId);

    AccountResponse updateByActNo(String actNo, UpdateAccountRequest updateAccountRequest);

    void deleteByActNo(String actNo);

    void disableByActNo(String actNo);
}
