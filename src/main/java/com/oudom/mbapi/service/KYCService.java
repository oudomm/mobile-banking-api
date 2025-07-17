package com.oudom.mbapi.service;

import com.oudom.mbapi.dto.KYCResponse;

public interface KYCService {
    KYCResponse verifyKYC(Integer customerId);
}
