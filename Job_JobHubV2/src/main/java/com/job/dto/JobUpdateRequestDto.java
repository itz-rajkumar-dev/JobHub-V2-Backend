package com.job.dto;

import com.job.entity.JobType;
import com.job.entity.WorkMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobUpdateRequestDto {

    private String title;

    private String description;

    private String responsibilities;

    private String requirements;

    private String qualifications;

    private String benefits;

    private Long categoryId;

    private BigDecimal salaryMin;

    private BigDecimal salaryMax;

    private String salaryCurrency;

    private Integer experienceMin;

    private Integer experienceMax;

    private JobType jobType;

    private WorkMode workMode;

    private String location;

    private List<String> skills;

    private Integer vacancies;

    private LocalDate deadline;
}