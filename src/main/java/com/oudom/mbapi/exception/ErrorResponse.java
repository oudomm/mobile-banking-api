package com.oudom.mbapi.exception;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse<T> (
        String message,
        int status,
        LocalDateTime timeSTamp,
        T details
){}
