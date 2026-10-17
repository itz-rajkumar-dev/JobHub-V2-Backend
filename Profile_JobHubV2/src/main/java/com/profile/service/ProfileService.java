package com.profile.service;

import com.profile.dto.ProfileRequestDto;
import com.profile.dto.ProfileResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProfileService {

    // Job Seeker
    ProfileResponseDto createProfile(ProfileRequestDto requestDto);

    ProfileResponseDto getMyProfile();

    ProfileResponseDto updateProfile(ProfileRequestDto requestDto, Long profileId);

    void deleteProfile(Long profileId);

    // Admin
    List<ProfileResponseDto> getAllProfiles();

    ProfileResponseDto getProfileById(Long profileId);

    void uploadCoverImage(Long profileId, MultipartFile file);

    ProfileResponseDto getProfileByUserId(Long userId);

    void createProfileForUser(Long userId,String role);
}