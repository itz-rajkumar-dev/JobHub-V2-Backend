package com.application.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationRequestDto {

    @NotNull(message = "Job ID is required")
    @Positive(message = "Job ID must be positive")
    private Long jobId;

    @NotNull(message = "Resume is required")
    private MultipartFile resume;

    private List<MultipartFile> additionalDocuments;

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

    @Builder.Default
    private Boolean openToRemote = false;

    @NotNull(message = "Work authorization is required")
    private Boolean workAuthorized;

    @NotNull(message = "Sponsorship information is required")
    private Boolean requiresSponsorship;
    private LocalDate availableFrom;
}