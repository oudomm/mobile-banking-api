package com.oudom.mbapi.util;

import com.oudom.mbapi.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class AccountNumberGenerator {

    private final AccountRepository accountRepository;
    private final SecureRandom random = new SecureRandom();

    public String generateAccountNumber() {
        String accountNumber;
        do {
            // Format: BBBB-CCCC-NNNN-NNNN
            // B = Bank code, C = Branch code, N = Account number
            String bankCode = "1234";
            String branchCode = String.format("%04d", random.nextInt(9999));
            String accountSeq = String.format("%08d", random.nextInt(99999999));

            accountNumber = bankCode + branchCode + accountSeq;
        } while (accountRepository.existsByActNo(accountNumber));

        return accountNumber;
    }
}
