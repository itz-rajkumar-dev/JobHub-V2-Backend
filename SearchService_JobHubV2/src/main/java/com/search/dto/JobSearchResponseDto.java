package com.search.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobSearchResponseDto {

    private Long jobId;

    private String title;

    private String description;

    private Long companyId;

    private Long categoryId;

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

    private String companyName;
    private String companyLogo;
    private String categoryName;

    private LocalDateTime createdAt;
}