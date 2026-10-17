package com.notification.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationSubmittedEventDto {

    private Long applicationId;

    private Long jobId;

    private Long applicantId;

    private Long companyId;

    private String applicantName;

    private String jobTitle;
}