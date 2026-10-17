package com.application.service;

import com.application.dto.ApplicationRequestDto;
import com.application.dto.ApplicationResponseDto;
import com.application.dto.ApplicationUpdateRequestDto;
import com.application.dto.PageResponseDto;
import com.application.entity.ApplicationStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ApplicationService {

    ApplicationResponseDto applyApplication(ApplicationRequestDto requestDto);

    List<ApplicationResponseDto> getAllApplication();

    ApplicationResponseDto getByApplicationId(Long applicationId);

    PageResponseDto<ApplicationResponseDto> getEmployerApplication(int page, int size, String search);

    ApplicationResponseDto updateApplication(ApplicationUpdateRequestDto requestDto, Long applicationId);

    void deleteApplication(Long applicationId);

    ApplicationResponseDto updateApplicationStatus(Long applicationId, ApplicationStatus status);

    long countApplicationsByCompany(Long companyId);
}