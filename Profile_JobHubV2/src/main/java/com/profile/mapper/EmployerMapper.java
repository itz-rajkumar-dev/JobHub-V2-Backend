package com.profile.mapper;

import com.profile.dto.EmployerRequestDto;
import com.profile.dto.EmployerResponseDto;
import com.profile.dto.EmployerUpdateRequestDto;
import com.profile.entity.EmployerProfile;

public class EmployerMapper {

    public static EmployerResponseDto toResponseDto(EmployerProfile employerProfile) {

        EmployerResponseDto dto = new EmployerResponseDto();

        dto.setEmployerProfileId(employerProfile.getEmployerProfileId());
        dto.setUserId(employerProfile.getUserId());
        dto.setFullName(employerProfile.getFullName());
        dto.setProfileImage(employerProfile.getProfileImage());
        dto.setHeadline(employerProfile.getHeadline());
        dto.setAbout(employerProfile.getAbout());
        dto.setPhone(employerProfile.getPhone());
        dto.setLocation(employerProfile.getLocation());
        dto.setLinkedinUrl(employerProfile.getLinkedinUrl());
        dto.setProfileImage(employerProfile.getProfileImage());
        dto.setCoverImage(employerProfile.getCoverImage());
        return dto;
    }

    public static void updateEmployer(
            EmployerProfile employerProfile,
            EmployerUpdateRequestDto employer) {

        if (employer.getFullName() != null
                && !employer.getFullName().isBlank()) {
            employerProfile.setFullName(employer.getFullName());
        }

        if (employer.getHeadline() != null
                && !employer.getHeadline().isBlank()) {
            employerProfile.setHeadline(employer.getHeadline());
        }

        if (employer.getAbout() != null
                && !employer.getAbout().isBlank()) {
            employerProfile.setAbout(employer.getAbout());
        }

        if (employer.getPhone() != null
                && !employer.getPhone().isBlank()) {
            employerProfile.setPhone(employer.getPhone());
        }

        if (employer.getLocation() != null
                && !employer.getLocation().isBlank()) {
            employerProfile.setLocation(employer.getLocation());
        }

        if (employer.getLinkedinUrl() != null
                && !employer.getLinkedinUrl().isBlank()) {
            employerProfile.setLinkedinUrl(employer.getLinkedinUrl());
        }
    }
}