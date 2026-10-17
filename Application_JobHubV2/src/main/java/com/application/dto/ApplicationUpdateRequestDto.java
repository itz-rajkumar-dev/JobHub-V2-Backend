package com.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ApplicationUpdateRequestDto {

    private MultipartFile resume;

    @Size(max = 5000, message = "Cover letter cannot exceed 5000 characters")
    private String coverLetter;

    @Size(max = 5000, message = "Why interested cannot exceed 5000 characters")
    private String whyInterested;

    @PositiveOrZero(message = "Experience cannot be negative")
    private Integer experienceYears;

    @PositiveOrZero(message = "Expected salary cannot be negative")
    private BigDecimal expectedSalary;

    @Size(max = 10, message = "Salary currency cannot exceed 10 characters")
    private String salaryCurrency;

    @NotBlank(message = "Location is required")
    @Size(max = 150, message = "Location cannot exceed 150 characters")
    private String applicantLocation;

    private Boolean openToRemote;

    private Boolean workAuthorized;

    private Boolean requiresSponsorship;

    private LocalDate availableFrom;
}