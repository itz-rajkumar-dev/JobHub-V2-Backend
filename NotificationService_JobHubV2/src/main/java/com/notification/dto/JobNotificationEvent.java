package com.notification.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobNotificationEvent {

    private String eventType;
    private Long receiverId;
    private Long jobId;

    private String title;
    private String description;
    private String responsibilities;
    private String requirements;
    private String qualifications;
    private String benefits;

    private Long companyId;
    private String companyName;
    private String companyLogo;
    private Long categoryId;
    private String categoryName;

    private BigDecimal salaryMin;
    private BigDecimal salaryMax;

    private Integer experienceMin;
    private Integer experienceMax;

    private String jobType;
    private String workMode;

    private String location;

    private List<String> skills;

    private Integer vacancies;

    private Long viewsCount;
    private Long applicationsCount;

    private Boolean featured;

    private LocalDate deadline;

    private String status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}