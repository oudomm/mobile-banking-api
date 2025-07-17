package com.oudom.mbapi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCustomerRequest(

        @NotBlank(message = "Full name is required")
        String fullName,

        @NotBlank(message = "Gender is required")
        String gender,

        String email,
        String phoneNumber,
        String remark,

        @NotNull(message = "Segment ID is required")
        Integer segmentId,

        @Valid
        CreateKYCRequest kyc
) {
}
