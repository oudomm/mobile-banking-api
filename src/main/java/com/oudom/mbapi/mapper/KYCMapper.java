package com.oudom.mbapi.mapper;

import com.oudom.mbapi.domain.KYC;
import com.oudom.mbapi.dto.CreateKYCRequest;
import com.oudom.mbapi.dto.KYCResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface KYCMapper {
    KYCResponse fromKYC(KYC kyc);
    KYC toKYC(CreateKYCRequest createKYCRequest);
}
