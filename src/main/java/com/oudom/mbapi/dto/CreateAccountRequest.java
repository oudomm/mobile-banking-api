package com.oudom.mbapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateAccountRequest(
        @NotBlank
        @Size(max = 50)
        String actCurrency,

        @NotNull
        BigDecimal balance,

        @NotNull
        Integer custId,

        @NotNull
        Integer actTypeId
) {
}
