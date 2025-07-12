package com.oudom.mbapi.dto;

import java.math.BigDecimal;

public record UpdateAccountRequest(
        String actCurrency,
        BigDecimal balance
) {
}
