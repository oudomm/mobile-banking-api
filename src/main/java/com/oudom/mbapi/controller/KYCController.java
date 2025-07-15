package com.oudom.mbapi.controller;

import com.oudom.mbapi.service.impl.KYCServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/kyc")
public class KYCController {

    private final KYCServiceImpl kycService;

    @PutMapping("{customerId}/verify")
    public void verifyKYC(@PathVariable Integer customerId) {
        kycService.verifyKYC(customerId);
    }
}
