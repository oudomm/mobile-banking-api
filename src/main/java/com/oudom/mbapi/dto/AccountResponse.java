package com.oudom.mbapi.dto;

import java.math.BigDecimal;

public record AccountResponse(
        String actNo,
        String actCurrency,
        BigDecimal balance,
        BigDecimal overLimit,
        Integer custId,
        Integer actTypeId
) {
}
