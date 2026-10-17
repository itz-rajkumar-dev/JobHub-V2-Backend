package com.dashboardservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardErrorResponse {

    private int status;

    private String message;

    private LocalDateTime timestamp;
}