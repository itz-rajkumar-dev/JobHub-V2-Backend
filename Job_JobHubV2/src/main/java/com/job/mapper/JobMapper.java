package com.job.mapper;

import com.job.dto.JobRequestDto;
import com.job.dto.JobResponseDto;
import com.job.dto.JobUpdateRequestDto;
import com.job.entity.Job;

import java.time.LocalDateTime;

public class JobMapper {

    public static Job toEntity(JobRequestDto dto) {

        Job job = new Job();

        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        job.setResponsibilities(dto.getResponsibilities());
        job.setRequirements(dto.getRequirements());
        job.setQualifications(dto.getQualifications());
        job.setBenefits(dto.getBenefits());
        job.setCompanyId(dto.getCompanyId());
        job.setCategoryId(dto.getCategoryId());
        job.setSalaryMin(dto.getSalaryMin());
        job.setSalaryMax(dto.getSalaryMax());
        job.setSalaryCurrency(dto.getSalaryCurrency());
        job.setExperienceMin(dto.getExperienceMin());
        job.setExperienceMax(dto.getExperienceMax());
        job.setJobType(dto.getJobType());
        job.setWorkMode(dto.getWorkMode());
        job.setLocation(dto.getLocation());
        job.setSkills(dto.getSkills());
        job.setVacancies(dto.getVacancies());
        job.setDeadline(dto.getDeadline());

        return job;
    }


    public static JobResponseDto toResponseDto(
            Job job,
            String companyName,
            String companyLogo,
            String categoryName
    ) {

        JobResponseDto responseDto = new JobResponseDto();

        responseDto.setJobId(job.getJobId());
        responseDto.setTitle(job.getTitle());
        responseDto.setSlug(job.getSlug());
        responseDto.setDescription(job.getDescription());
        responseDto.setResponsibilities(job.getResponsibilities());
        responseDto.setRequirements(job.getRequirements());
        responseDto.setQualifications(job.getQualifications());
        responseDto.setBenefits(job.getBenefits());

        responseDto.setCompanyId(job.getCompanyId());
        responseDto.setCompanyName(companyName);
        responseDto.setCompanyLogo(companyLogo);

        responseDto.setCategoryId(job.getCategoryId());
        responseDto.setCategoryName(categoryName);

        responseDto.setPostedBy(job.getPostedBy());

        responseDto.setSalaryMin(job.getSalaryMin());
        responseDto.setSalaryMax(job.getSalaryMax());
        responseDto.setSalaryCurrency(job.getSalaryCurrency());

        responseDto.setExperienceMin(job.getExperienceMin());
        responseDto.setExperienceMax(job.getExperienceMax());

        responseDto.setJobType(job.getJobType());
        responseDto.setWorkMode(job.getWorkMode());
        responseDto.setLocation(job.getLocation());

        responseDto.setSkills(job.getSkills());
        responseDto.setVacancies(job.getVacancies());

        responseDto.setViewsCount(job.getViewsCount());
        responseDto.setApplicationsCount(job.getApplicationsCount());
        responseDto.setFeatured(job.getFeatured());

        responseDto.setPostedAt(job.getPostedAt());
        responseDto.setDeadline(job.getDeadline());
        responseDto.setStatus(job.getStatus());

        responseDto.setCreatedAt(job.getCreatedAt());
        responseDto.setUpdatedAt(job.getUpdatedAt());

        return responseDto;
    }
    public static void updateEntity(JobUpdateRequestDto dto, Job job) {

        if (dto.getTitle() != null)
            job.setTitle(dto.getTitle());

        if (dto.getDescription() != null)
            job.setDescription(dto.getDescription());

        if (dto.getResponsibilities() != null)
            job.setResponsibilities(dto.getResponsibilities());

        if (dto.getRequirements() != null)
            job.setRequirements(dto.getRequirements());

        if (dto.getQualifications() != null)
            job.setQualifications(dto.getQualifications());

        if (dto.getBenefits() != null)
            job.setBenefits(dto.getBenefits());

        if (dto.getCategoryId() != null)
            job.setCategoryId(dto.getCategoryId());

        if (dto.getSalaryMin() != null)
            job.setSalaryMin(dto.getSalaryMin());

        if (dto.getSalaryMax() != null)
            job.setSalaryMax(dto.getSalaryMax());

        if (dto.getSalaryCurrency() != null)
            job.setSalaryCurrency(dto.getSalaryCurrency());

        if (dto.getExperienceMin() != null)
            job.setExperienceMin(dto.getExperienceMin());

        if (dto.getExperienceMax() != null)
            job.setExperienceMax(dto.getExperienceMax());

        if (dto.getJobType() != null)
            job.setJobType(dto.getJobType());

        if (dto.getWorkMode() != null)
            job.setWorkMode(dto.getWorkMode());

        if (dto.getLocation() != null)
            job.setLocation(dto.getLocation());

        if (dto.getSkills() != null)
            job.setSkills(dto.getSkills());

        if (dto.getVacancies() != null)
            job.setVacancies(dto.getVacancies());

        if (dto.getDeadline() != null)
            job.setDeadline(dto.getDeadline());
    }
}