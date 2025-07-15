package com.oudom.mbapi.dto;

public record KYCResponse(
        String nationalCardId,
        Boolean isVerified
) {
}
