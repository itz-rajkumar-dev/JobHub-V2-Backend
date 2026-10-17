package com.application.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "applications",
        indexes = {
                @Index(name = "idx_application_job", columnList = "job_id"),
                @Index(name = "idx_application_applicant", columnList = "applicant_id"),
                @Index(name = "idx_application_company", columnList = "company_id"),
                @Index(name = "idx_application_status", columnList = "status")
        },
        uniqueConstraints = {@UniqueConstraint(
                        name = "uk_applicant_job",
                        columnNames = {"applicant_id", "job_id"})
        }
)
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    // References
    @Column(name = "job_id", nullable = false)
    private Long jobId;

    @Column(name = "applicant_id", nullable = false)
    private Long applicantId;

    @Column(name = "company_id", nullable = false)
    private Long companyId;
    @Column(nullable = false, length = 150)
    private String applicantName;

    @Column(length = 20)
    private String applicantPhone;

    @Column(nullable = false, length = 150)
    private String applicantEmail;

    @Column(length = 150)
    private String applicantLocation;
    private Integer experienceYears;
    @Column(precision = 12, scale = 2)
    private BigDecimal expectedSalary;

    @Column(length = 10)
    private String salaryCurrency;

    @Builder.Default
    private Boolean openToRemote = false;

    @Builder.Default
    private Boolean workAuthorized = false;

    @Builder.Default
    private Boolean requiresSponsorship = false;

    @Column(columnDefinition = "TEXT")
    private String coverLetter;

    @Column(columnDefinition = "TEXT")
    private String whyInterested;


    // Resume
    @Column(length = 500)
    private String resumeUrl;

    @Column(length = 255)
    private String resumeFileName;

    // Additional Documents
    @ElementCollection
    @CollectionTable(
            name = "application_additional_documents",
            joinColumns = @JoinColumn(name = "application_id")
    )
    @Column(name = "document_url", length = 500)
    private List<String> additionalDocumentUrls;

    @ElementCollection
    @CollectionTable(
            name = "application_additional_document_names",
            joinColumns = @JoinColumn(name = "application_id")
    )
    @Column(name = "document_file_name", length = 255)
    private List<String> additionalDocumentFileNames;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Builder.Default
    private ApplicationStatus status = ApplicationStatus.APPLIED;

    @Column(columnDefinition = "TEXT")
    private String employerNotes;

    private LocalDate availableFrom;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime appliedAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}