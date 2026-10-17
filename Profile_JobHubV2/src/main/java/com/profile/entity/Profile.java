package com.profile.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "profiles",
        uniqueConstraints = {@UniqueConstraint(
                        name = "uk_profile_user",
                        columnNames = "user_id")
        },
        indexes = {
                @Index(name = "idx_profile_user", columnList = "user_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(length = 150)
    private String headline;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @ElementCollection
    @CollectionTable(name = "profile_skills", joinColumns = @JoinColumn(name = "profile_id")
    )
    @Column(name = "skill", nullable = false)
    @Builder.Default
    private List<String> skills = new ArrayList<>();

    @Column(length = 255)
    private String projectName;

    @Column(columnDefinition = "TEXT")
    private String projectDescription;

    private LocalDate projectStartDate;

    @Builder.Default
    private Boolean currentlyWorkingOnProject = false;

    @Column(length = 100)
    private String currentJobTitle;

    @Column(length = 150)
    private String currentCompany;

    private Integer totalExperienceYears;

    @Column(length = 150)
    private String highestEducation;

    @Column(length = 150)
    private String university;

    @Column(length = 100)
    private String fieldOfStudy;

    private Integer graduationYear;

    @Column(length = 500)
    private String resumeUrl;

    @Column(length = 500)
    private String linkedinUrl;

    @Column(length = 500)
    private String githubUrl;

    @Column(length = 500)
    private String portfolioUrl;

    @Column(length = 100)
    private String preferredJobTitle;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private WorkPreference workPreference;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private AvailabilityStatus availabilityStatus;

    private Double cgpa;

    @Column(length = 150)
    private String collegeLocation;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private ExperienceLevel experienceLevel;

    @Column(length = 500)
    private String coverImage;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

}