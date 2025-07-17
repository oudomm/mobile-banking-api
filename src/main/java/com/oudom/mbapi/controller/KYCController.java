package com.oudom.mbapi.controller;

import com.oudom.mbapi.dto.KYCResponse;
import com.oudom.mbapi.mapper.KYCMapper;
import com.oudom.mbapi.service.KYCService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/kyc")
public class KYCController {

    private final KYCService kycService;

    @PutMapping("{customerId}/verify")
    public KYCResponse verifyKYC(@PathVariable Integer customerId) {
        return kycService.verifyKYC(customerId);
    }
}
