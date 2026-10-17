package com.search.dto;

import com.search.validation.ValidExperienceRange;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import com.search.validation.ValidSalaryRange;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ValidSalaryRange
@ValidExperienceRange
public class SearchRequestDto {


    @Size(max = 100, message = "Keyword cannot exceed 100 characters")
    private String keyword;

    @Size(max = 100, message = "Location cannot exceed 100 characters")
    private String location;

    @Pattern(
            regexp = "FULL_TIME|PART_TIME|CONTRACT|INTERNSHIP|FREELANCE",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Invalid jobType. Allowed values: FULL_TIME, PART_TIME, CONTRACT, INTERNSHIP, FREELANCE"
    )
    private String jobType;
    @Pattern(
            regexp = "ONSITE|REMOTE|HYBRID",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Invalid workMode. Allowed values: ONSITE, REMOTE, HYBRID"
    )
    private String workMode;

    @Min(value = 1, message = "Category ID must be positive")
    private Long categoryId;

    @Min(value = 1, message = "Company ID must be positive")
    private Long companyId;

    @DecimalMin(value = "0.0", message = "Minimum salary cannot be negative")
    private BigDecimal minSalary;

    @DecimalMin(value = "0.0", message = "Maximum salary cannot be negative")
    private BigDecimal maxSalary;

    @Min(value = 0, message = "Minimum experience cannot be negative")
    private Integer minExperience;

    @Min(value = 0, message = "Maximum experience cannot be negative")
    private Integer maxExperience;

    @Size(max = 50, message = "Skill cannot exceed 50 characters")
    private String skill;

    @Pattern(
            regexp = "createdAt|title|salaryMin|salaryMax|experienceMin|experienceMax",
            message = "Invalid sortBy. Allowed values: createdAt, title, salaryMin, salaryMax, experienceMin, experienceMax"
    )
    @Builder.Default
    private String sortBy = "createdAt";

    @Pattern(
            regexp = "asc|desc",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Sort direction must be asc or desc"
    )
    @Builder.Default
    private String sortDirection = "desc";

    @Builder.Default
    @Min(value = 0, message = "Page cannot be negative")
    private Integer page = 0;

    @Builder.Default
    @Min(value = 1, message = "Size must be at least 1")
    private Integer size = 10;
}