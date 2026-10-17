package com.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplicationStatusUpdatedEventDto {

    private Long applicationId;
    private Long applicantId;
    private Long companyId;
    private Long jobId;
    private String applicantName;
    private String jobTitle;
    private String status;
}