package com.job.dto;

import com.job.entity.JobType;
import com.job.entity.WorkMode;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobRequestDto {


    @NotBlank(message = "Title is required")
    @Size(max = 150, message = "Title cannot exceed 150 characters")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Responsibilities are required")
    private String responsibilities;

    @NotBlank(message = "Requirements are required")
    private String requirements;

    @NotBlank(message = "Qualifications are required")
    private String qualifications;

    private String benefits;


    @NotNull(message = "Company is required")
    @Positive(message = "Company ID must be positive")
    private Long companyId;

    @NotNull(message = "Category is required")
    @Positive(message = "Category ID must be positive")
    private Long categoryId;


    @PositiveOrZero(message = "Minimum salary cannot be negative")
    private BigDecimal salaryMin;

    @PositiveOrZero(message = "Maximum salary cannot be negative")
    private BigDecimal salaryMax;

    @Size(max = 10, message = "Currency cannot exceed 10 characters")
    private String salaryCurrency;

    @PositiveOrZero(message = "Minimum experience cannot be negative")
    private Integer experienceMin;

    @PositiveOrZero(message = "Maximum experience cannot be negative")
    private Integer experienceMax;


    @NotNull(message = "Job type is required")
    private JobType jobType;

    @NotNull(message = "Work mode is required")
    private WorkMode workMode;

    @NotBlank(message = "Location is required")
    private String location;


    @NotEmpty(message = "At least one skill is required")
    private List<@NotBlank(message = "Skill cannot be blank") String> skills = new ArrayList<>();

    @NotNull(message = "Vacancies are required")
    @Positive(message = "Vacancies must be greater than 0")
    private Integer vacancies;

    @NotNull(message = "Deadline is required")
    @Future(message = "Deadline must be a future date")
    private LocalDate deadline;
}