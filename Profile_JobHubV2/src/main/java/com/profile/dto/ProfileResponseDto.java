package com.profile.dto;

import com.profile.entity.AvailabilityStatus;
import com.profile.entity.ExperienceLevel;
import com.profile.entity.WorkPreference;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileResponseDto {

    private Long profileId;

    private Long userId;

    private String headline;

    private String summary;

    private String projectName;
    private String projectDescription;
    private LocalDate projectStartDate;
    private Boolean currentlyWorkingOnProject;
    private List<String> skills = new ArrayList<>();

    private String currentJobTitle;

    private String currentCompany;

    private Integer totalExperienceYears;

    private String highestEducation;

    private String university;

    private String fieldOfStudy;

    private Integer graduationYear;

    private String resumeUrl;

    private String linkedinUrl;

    private String githubUrl;

    private String portfolioUrl;

    private String preferredJobTitle;

    private WorkPreference workPreference;

    private AvailabilityStatus availabilityStatus;

    private Double cgpa;
    private String collegeLocation;
    private ExperienceLevel experienceLevel;
    private String coverImage;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
