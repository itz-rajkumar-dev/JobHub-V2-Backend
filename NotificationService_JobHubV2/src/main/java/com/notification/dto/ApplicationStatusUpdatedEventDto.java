package com.notification.dto;

import lombok.Data;

@Data
public class ApplicationStatusUpdatedEventDto {

    private Long applicationId;
    private Long applicantId;
    private Long companyId;
    private Long jobId;
    private String applicantName;
    private String jobTitle;
    private String status;
}