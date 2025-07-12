package com.oudom.mbapi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleServiceException(ResponseStatusException e) {

        String message;

        if (e.getStatusCode().value() == 400) {
            message = "Invalid request";
        } else {
            message = "Business logic error";
        }

        ErrorResponse<String> error = ErrorResponse.<String>builder()
                .message(message)
                .status(e.getStatusCode().value())
                .timeSTamp(LocalDateTime.now())
                .details(e.getReason())
                .build();

        return ResponseEntity
                .status(e.getStatusCode().value())
                .body(error);
    }
}