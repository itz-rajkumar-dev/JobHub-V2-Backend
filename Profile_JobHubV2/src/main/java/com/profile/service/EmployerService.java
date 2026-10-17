package com.profile.service;

import com.profile.dto.EmployerResponseDto;
import com.profile.dto.EmployerUpdateRequestDto;
import com.profile.entity.EmployerProfile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmployerService {


    EmployerResponseDto getMyProfile();

    EmployerResponseDto getProfileByUserId(Long userId);

    EmployerResponseDto updateEmployerProfile(EmployerUpdateRequestDto employer, Long employerId);

    void deleteEmployerProfile(Long employerId);

    void uploadProfileImage(Long userId, MultipartFile file);

    void uploadCoverImage(Long userId, MultipartFile file);
}
