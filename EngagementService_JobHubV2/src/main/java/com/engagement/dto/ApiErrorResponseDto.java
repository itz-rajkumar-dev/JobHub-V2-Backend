package com.engagement.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorResponseDto {

    private LocalDateTime timestamp;
    private int status;
    private String message;
    private Map<String, String> error;

    public ApiErrorResponseDto(
            LocalDateTime timestamp,
            int status,
            String message) {

        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.error = null;
    }
}
