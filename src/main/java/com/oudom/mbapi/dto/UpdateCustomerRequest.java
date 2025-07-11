package com.oudom.mbapi.dto;

public record UpdateCustomerRequest(
        String fullName,
        String gender,
        String remark
) {
}
