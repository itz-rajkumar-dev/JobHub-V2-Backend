package com.application.dto;

import com.application.entity.ApplicationStatus;
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
public class ApplicationResponseDto {

    private Long applicationId;

    private Long jobId;

    private Long applicantId;

    private Long companyId;

    private String companyName;
    private String companyLogo;
    private String profileImage;
    private String jobTitle;

    private String applicantName;

    private String applicantEmail;

    private String applicantPhone;

    private String applicantLocation;

    private Integer experienceYears;

    private BigDecimal expectedSalary;

    private String salaryCurrency;

    private Boolean openToRemote;

    private Boolean workAuthorized;

    private Boolean requiresSponsorship;

    private String coverLetter;

    private String whyInterested;

    private String resumeUrl;

    private String resumeFileName;

    private List<String> additionalDocumentUrls;

    private List<String> additionalDocumentFileNames;

    private ApplicationStatus status;

    private String employerNotes;

    private LocalDate availableFrom;

    private LocalDateTime appliedAt;

    private LocalDateTime updatedAt;
}