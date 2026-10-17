package com.profile.dto;

import com.profile.entity.AvailabilityStatus;
import com.profile.entity.ExperienceLevel;
import com.profile.entity.WorkPreference;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileRequestDto {

    @Size(max = 150, message = "Headline cannot exceed 150 characters")
    private String headline;

    @Size(max = 5000, message = "Summary cannot exceed 5000 characters")
    private String summary;

    @Size(max = 30, message = "Skills cannot exceed 30 items")
    @Builder.Default
    private List<String> skills = new ArrayList<>();

    @Size(max = 100, message = "Summary cannot exceed 100 characters")
    private String projectName;

    @Size(max = 5000)
    private String projectDescription;

    private LocalDate projectStartDate;

    private Boolean currentlyWorkingOnProject;

    @Size(max = 100, message = "Current job title cannot exceed 100 characters")
    private String currentJobTitle;

    @Size(max = 150, message = "Current company cannot exceed 150 characters")
    private String currentCompany;

    @Min(value = 0, message = "Experience cannot be negative")
    @Max(value = 60, message = "Experience cannot exceed 60 years")
    private Integer totalExperienceYears;

    @Size(max = 150, message = "Education cannot exceed 150 characters")
    private String highestEducation;

    @Size(max = 150, message = "University cannot exceed 150 characters")
    private String university;

    @Size(max = 100, message = "Field of study cannot exceed 100 characters")
    private String fieldOfStudy;

    @Min(value = 1900, message = "Invalid graduation year")
    @Max(value = 2100, message = "Invalid graduation year")
    private Integer graduationYear;

    @Size(max = 500, message = "Resume URL cannot exceed 500 characters")
    private String resumeUrl;

    @Size(max = 500, message = "LinkedIn URL cannot exceed 500 characters")
    private String linkedinUrl;

    @Size(max = 500, message = "GitHub URL cannot exceed 500 characters")
    private String githubUrl;

    @Size(max = 500, message = "Portfolio URL cannot exceed 500 characters")
    private String portfolioUrl;

    @Size(max = 100, message = "Preferred job title cannot exceed 100 characters")
    private String preferredJobTitle;

    @Min(value = 0, message = "CGPA cannot be negative")
    @Max(value = 10, message = "CGPA cannot exceed 10")
    private Double cgpa;

    @Size(max = 150, message = "College location cannot exceed 150 characters")
    private String collegeLocation;

    private ExperienceLevel experienceLevel;

    @Size(max = 500, message = "Cover image URL cannot exceed 500 characters")
    private String coverImage;

    private WorkPreference workPreference;

    private AvailabilityStatus availabilityStatus;
}