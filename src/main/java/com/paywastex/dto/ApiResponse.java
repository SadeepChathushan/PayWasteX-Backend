package com.paywastex.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse <T> {
    private LocalDateTime timestamp;
    private String message;
    private T data;
    private int status;

    public ApiResponse(Instant now, String m, Object data, int value) {
    }
}

