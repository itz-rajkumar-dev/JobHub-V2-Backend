package com.job.dto;

import com.job.entity.JobStatus;
import com.job.entity.JobType;
import com.job.entity.WorkMode;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobResponseDto {

    private Long jobId;

    private String title;
    private String slug;
    private String description;
    private String responsibilities;
    private String requirements;
    private String qualifications;
    private String benefits;
    private Long companyId;
    private Long categoryId;
    private String companyName;
    private String companyLogo;
    private String categoryName;
    private Long postedBy;
    private BigDecimal salaryMin;
    private BigDecimal salaryMax;
    private String salaryCurrency;
    private Integer experienceMin;
    private Integer experienceMax;
    private JobType jobType;
    private WorkMode workMode;
    private String location;

    @Builder.Default
    private List<String> skills = new ArrayList<>();

    private Integer vacancies;
    private Long viewsCount;
    private Long applicationsCount;
    private Boolean featured;
    private LocalDateTime postedAt;
    private LocalDate deadline;
    private JobStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}