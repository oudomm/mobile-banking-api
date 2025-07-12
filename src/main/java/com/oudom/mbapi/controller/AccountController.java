package com.oudom.mbapi.controller;

import com.oudom.mbapi.dto.AccountResponse;
import com.oudom.mbapi.dto.CreateAccountRequest;
import com.oudom.mbapi.dto.UpdateAccountRequest;
import com.oudom.mbapi.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AccountResponse createAccount(@Valid @RequestBody CreateAccountRequest createAccountRequest) {
        return accountService.createAccount(createAccountRequest);
    }

    @GetMapping
    public List<AccountResponse> getAccounts(
            @RequestParam(value = "custId", required = false) Integer custId,
            HttpServletRequest request) {

        // Check all query parameters
        var queryParams = request.getParameterMap();

        for (String param : queryParams.keySet()) {
            if (!param.equals("custId")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown query parameter: " + param);
            }
        }

        // Find accounts by customer
        if (custId != null) {
            return accountService.findByCustId(custId);
        }

        // Find all accounts
        return accountService.findAll();
    }

    @GetMapping("{actNo}")
    public AccountResponse getAccountByActNo(@PathVariable String actNo) {
        return accountService.findByActNo(actNo);
    }

    @PatchMapping("{actNo}")
    public AccountResponse updateByActNo(
            @PathVariable String actNo,
            @Valid @RequestBody UpdateAccountRequest updateAccountRequest) {
        return accountService.updateByActNo(actNo, updateAccountRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{actNo}")
    public void deleteByActNo(@PathVariable String actNo) {
        accountService.deleteByActNo(actNo);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("{actNo}/disable")
    public void disableByActNo(@PathVariable String actNo) {
        accountService.disableByActNo(actNo);
    }

}
