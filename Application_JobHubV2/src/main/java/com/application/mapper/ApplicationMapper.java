package com.application.mapper;

import com.application.dto.ApplicationRequestDto;
import com.application.dto.ApplicationResponseDto;
import com.application.dto.ApplicationUpdateRequestDto;
import com.application.entity.Application;

public class ApplicationMapper {

    public static Application toEntity(ApplicationRequestDto dto) {

        Application application = new Application();

        application.setJobId(dto.getJobId());
        application.setApplicantLocation(dto.getApplicantLocation());
        application.setExperienceYears(dto.getExperienceYears());
        application.setExpectedSalary(dto.getExpectedSalary());
        application.setSalaryCurrency(dto.getSalaryCurrency());
        application.setOpenToRemote(dto.getOpenToRemote());
        application.setWorkAuthorized(dto.getWorkAuthorized());
        application.setRequiresSponsorship(dto.getRequiresSponsorship());
        application.setCoverLetter(dto.getCoverLetter());
        application.setWhyInterested(dto.getWhyInterested());
        application.setAvailableFrom(dto.getAvailableFrom());


        return application;
    }


    public static ApplicationResponseDto toResponseDto(Application application) {

        ApplicationResponseDto dto = new ApplicationResponseDto();

        dto.setApplicationId(application.getApplicationId());
        dto.setJobId(application.getJobId());
        dto.setApplicantId(application.getApplicantId());
        dto.setCompanyId(application.getCompanyId());
        dto.setApplicantName(application.getApplicantName());
        dto.setApplicantEmail(application.getApplicantEmail());
        dto.setApplicantPhone(application.getApplicantPhone());
        dto.setApplicantLocation(application.getApplicantLocation());
        dto.setExperienceYears(application.getExperienceYears());
        dto.setExpectedSalary(application.getExpectedSalary());
        dto.setSalaryCurrency(application.getSalaryCurrency());
        dto.setOpenToRemote(application.getOpenToRemote());
        dto.setWorkAuthorized(application.getWorkAuthorized());
        dto.setRequiresSponsorship(application.getRequiresSponsorship());

        dto.setCoverLetter(application.getCoverLetter());
        dto.setWhyInterested(application.getWhyInterested());
        dto.setResumeUrl(application.getResumeUrl());
        dto.setResumeFileName(application.getResumeFileName());
        dto.setStatus(application.getStatus());
        dto.setEmployerNotes(application.getEmployerNotes());
        dto.setAvailableFrom(application.getAvailableFrom());
        dto.setAppliedAt(application.getAppliedAt());
        dto.setUpdatedAt(application.getUpdatedAt());
        dto.setAdditionalDocumentUrls(application.getAdditionalDocumentUrls());
        dto.setAdditionalDocumentFileNames(application.getAdditionalDocumentFileNames());



        return dto;
    }

    public static ApplicationResponseDto toResponseDto(
            Application application,
            String companyName,
            String companyLogo,
            String jobTitle,
            String profileImage
    ) {
        ApplicationResponseDto dto = toResponseDto(application);

        dto.setCompanyName(companyName);
        dto.setCompanyLogo(companyLogo);
        dto.setJobTitle(jobTitle);
        dto.setProfileImage(profileImage);

        return dto;
    }


    public static void updateEntity(
            ApplicationUpdateRequestDto dto,
            Application application) {

        application.setApplicantLocation(dto.getApplicantLocation());
        application.setExperienceYears(dto.getExperienceYears());
        application.setExpectedSalary(dto.getExpectedSalary());
        application.setSalaryCurrency(dto.getSalaryCurrency());
        application.setOpenToRemote(dto.getOpenToRemote());
        application.setWorkAuthorized(dto.getWorkAuthorized());
        application.setRequiresSponsorship(dto.getRequiresSponsorship());
        application.setCoverLetter(dto.getCoverLetter());
        application.setWhyInterested(dto.getWhyInterested());
        application.setAvailableFrom(dto.getAvailableFrom());
    }
}