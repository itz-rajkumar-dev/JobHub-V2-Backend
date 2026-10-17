package com.profile.mapper;

import com.profile.dto.ProfileRequestDto;
import com.profile.dto.ProfileResponseDto;
import com.profile.entity.Profile;

public class ProfileMapper {

    public static Profile toEntity(ProfileRequestDto dto) {

        Profile profile = new Profile();

        profile.setHeadline(dto.getHeadline());
        profile.setSummary(dto.getSummary());
        profile.setSkills(dto.getSkills());
        profile.setCurrentJobTitle(dto.getCurrentJobTitle());
        profile.setCurrentCompany(dto.getCurrentCompany());
        profile.setTotalExperienceYears(dto.getTotalExperienceYears());
        profile.setHighestEducation(dto.getHighestEducation());
        profile.setUniversity(dto.getUniversity());
        profile.setFieldOfStudy(dto.getFieldOfStudy());
        profile.setGraduationYear(dto.getGraduationYear());
        profile.setResumeUrl(dto.getResumeUrl());
        profile.setLinkedinUrl(dto.getLinkedinUrl());
        profile.setGithubUrl(dto.getGithubUrl());
        profile.setPortfolioUrl(dto.getPortfolioUrl());
        profile.setPreferredJobTitle(dto.getPreferredJobTitle());
        profile.setWorkPreference(dto.getWorkPreference());
        profile.setAvailabilityStatus(dto.getAvailabilityStatus());
        profile.setCgpa(dto.getCgpa());
        profile.setCollegeLocation(dto.getCollegeLocation());
        profile.setExperienceLevel(dto.getExperienceLevel());
        profile.setCoverImage(dto.getCoverImage());
       profile.setProjectName(dto.getProjectName());
       profile.setProjectDescription(dto.getProjectDescription());
       profile.setProjectStartDate(dto.getProjectStartDate());
        profile.setCurrentlyWorkingOnProject(dto.getCurrentlyWorkingOnProject());

        return profile;
    }

    public static ProfileResponseDto toResponseDto(Profile profile) {

        ProfileResponseDto dto = new ProfileResponseDto();

        dto.setProfileId(profile.getProfileId());
        dto.setUserId(profile.getUserId());
        dto.setHeadline(profile.getHeadline());
        dto.setSummary(profile.getSummary());
        dto.setSkills(profile.getSkills());
        dto.setCurrentJobTitle(profile.getCurrentJobTitle());
        dto.setCurrentCompany(profile.getCurrentCompany());
        dto.setTotalExperienceYears(profile.getTotalExperienceYears());
        dto.setHighestEducation(profile.getHighestEducation());
        dto.setUniversity(profile.getUniversity());
        dto.setFieldOfStudy(profile.getFieldOfStudy());
        dto.setGraduationYear(profile.getGraduationYear());
        dto.setResumeUrl(profile.getResumeUrl());
        dto.setLinkedinUrl(profile.getLinkedinUrl());
        dto.setGithubUrl(profile.getGithubUrl());
        dto.setPortfolioUrl(profile.getPortfolioUrl());
        dto.setPreferredJobTitle(profile.getPreferredJobTitle());
        dto.setWorkPreference(profile.getWorkPreference());
        dto.setAvailabilityStatus(profile.getAvailabilityStatus());
        dto.setCreatedAt(profile.getCreatedAt());
        dto.setUpdatedAt(profile.getUpdatedAt());
        dto.setCgpa(profile.getCgpa());
        dto.setCollegeLocation(profile.getCollegeLocation());
        dto.setExperienceLevel(profile.getExperienceLevel());
        dto.setCoverImage(profile.getCoverImage());
       dto.setProjectName(profile.getProjectName());
       dto.setProjectDescription(profile.getProjectDescription());
       dto.setProjectStartDate(profile.getProjectStartDate());
        dto.setCurrentlyWorkingOnProject(profile.getCurrentlyWorkingOnProject());

        return dto;
    }

    public static void updateEntity(
            ProfileRequestDto dto,
            Profile profile) {

        if (dto.getHeadline() != null) {
            profile.setHeadline(dto.getHeadline());
        }

        if (dto.getSummary() != null) {
            profile.setSummary(dto.getSummary());
        }

        if (dto.getSkills() != null) {
            profile.setSkills(dto.getSkills());
        }

        if (dto.getCurrentJobTitle() != null) {
            profile.setCurrentJobTitle(dto.getCurrentJobTitle());
        }

        if (dto.getCurrentCompany() != null) {
            profile.setCurrentCompany(dto.getCurrentCompany());
        }

        if (dto.getTotalExperienceYears() != null) {
            profile.setTotalExperienceYears(dto.getTotalExperienceYears());
        }

        if (dto.getHighestEducation() != null) {
            profile.setHighestEducation(dto.getHighestEducation());
        }

        if (dto.getUniversity() != null) {
            profile.setUniversity(dto.getUniversity());
        }

        if (dto.getFieldOfStudy() != null) {
            profile.setFieldOfStudy(dto.getFieldOfStudy());
        }

        if (dto.getGraduationYear() != null) {
            profile.setGraduationYear(dto.getGraduationYear());
        }

        if (dto.getResumeUrl() != null) {
            profile.setResumeUrl(dto.getResumeUrl());
        }

        if (dto.getLinkedinUrl() != null) {
            profile.setLinkedinUrl(dto.getLinkedinUrl());
        }

        if (dto.getGithubUrl() != null) {
            profile.setGithubUrl(dto.getGithubUrl());
        }

        if (dto.getPortfolioUrl() != null) {
            profile.setPortfolioUrl(dto.getPortfolioUrl());
        }

        if (dto.getPreferredJobTitle() != null) {
            profile.setPreferredJobTitle(dto.getPreferredJobTitle());
        }

        if (dto.getWorkPreference() != null) {
            profile.setWorkPreference(dto.getWorkPreference());
        }

        if (dto.getAvailabilityStatus() != null) {
            profile.setAvailabilityStatus(dto.getAvailabilityStatus());
        }
        if (dto.getCgpa()!=null){
            profile.setCgpa(dto.getCgpa());
        }
        if(dto.getCollegeLocation()!=null){
            profile.setCollegeLocation(dto.getCollegeLocation());
        }
        if (dto.getExperienceLevel()!=null){
            profile.setExperienceLevel(dto.getExperienceLevel());
        }
        if (dto.getCoverImage() != null) {
            profile.setCoverImage(dto.getCoverImage());
        }

        if (dto.getProjectName() != null) {
            profile.setProjectName(dto.getProjectName());
        }

        if (dto.getProjectDescription() != null) {
            profile.setProjectDescription(dto.getProjectDescription());
        }

        if (dto.getProjectStartDate() != null) {
            profile.setProjectStartDate(dto.getProjectStartDate());
        }
        if (dto.getCurrentlyWorkingOnProject() != null) {
            profile.setCurrentlyWorkingOnProject(dto.getCurrentlyWorkingOnProject());
        }


    }
}