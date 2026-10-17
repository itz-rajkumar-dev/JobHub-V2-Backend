package com.job.event;

import com.job.entity.JobStatus;
import com.job.entity.JobType;
import com.job.entity.WorkMode;
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
public class JobSearchEvent {

    private String eventType;

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

    private JobType jobType;
    private WorkMode workMode;

    private String location;

    private List<String> skills;

    private Integer vacancies;

    private Long viewsCount;
    private Long applicationsCount;

    private Boolean featured;

    private LocalDate deadline;

    private JobStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
