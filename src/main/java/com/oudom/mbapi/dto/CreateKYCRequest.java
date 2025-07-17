package com.oudom.mbapi.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateKYCRequest(
        @NotBlank(message = "National Card ID is required")
        String nationalCardId
) {
}
