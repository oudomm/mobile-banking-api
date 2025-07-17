package com.oudom.mbapi.service.impl;

import com.oudom.mbapi.domain.KYC;
import com.oudom.mbapi.dto.KYCResponse;
import com.oudom.mbapi.mapper.KYCMapper;
import com.oudom.mbapi.repository.KYCRepository;
import com.oudom.mbapi.service.KYCService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class KYCServiceImpl implements KYCService {

    private final KYCRepository kycRepository;
    private final KYCMapper kycMapper;

    @Override
    public KYCResponse verifyKYC(Integer customerId) {
        KYC kyc = kycRepository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "KYC not found"));

        if (kyc.getIsVerified()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "KYC is already verified");
        }

        kyc.setIsVerified(true);

        return kycMapper.fromKYC(kycRepository.save(kyc));
    }

}
