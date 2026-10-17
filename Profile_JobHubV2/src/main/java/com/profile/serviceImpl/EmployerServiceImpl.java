package com.profile.serviceImpl;

import com.profile.dto.EmployerResponseDto;
import com.profile.dto.EmployerUpdateRequestDto;
import com.profile.entity.EmployerProfile;
import com.profile.exception.EmployerProfileNotFoundException;
import com.profile.exception.UnauthorizedException;
import com.profile.mapper.EmployerMapper;
import com.profile.repository.EmployerProfileRepository;
import com.profile.security.AuthenticatedUser;
import com.profile.service.CloudinaryService;
import com.profile.service.EmployerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EmployerServiceImpl implements EmployerService {

    @Autowired
    private CloudinaryService cloudinaryService;

    private final EmployerProfileRepository employerProfileRepository;

    public EmployerServiceImpl(
            EmployerProfileRepository employerProfileRepository) {
        this.employerProfileRepository = employerProfileRepository;
    }

    private AuthenticatedUser getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !(authentication.getPrincipal() instanceof AuthenticatedUser)) {

            throw new UnauthorizedException("User Not Found");
        }

        return (AuthenticatedUser) authentication.getPrincipal();
    }

    @Override
    public EmployerResponseDto getMyProfile() {

        AuthenticatedUser user = getCurrentUser();

        EmployerProfile employerProfile = employerProfileRepository.findByUserId(user.getUserId())
                        .orElseThrow(() -> new EmployerProfileNotFoundException("Employer Profile Not Found"));

        return EmployerMapper.toResponseDto(employerProfile);
    }

    @Override
    public EmployerResponseDto getProfileByUserId(Long userId) {

        EmployerProfile employerProfile = employerProfileRepository.findByUserId(userId)
                        .orElseThrow(() -> new EmployerProfileNotFoundException("Employer Profile Not Found for user: " + userId));

        return EmployerMapper.toResponseDto(employerProfile);
    }

    @Override
    public EmployerResponseDto updateEmployerProfile(EmployerUpdateRequestDto employer, Long userId) {

        AuthenticatedUser user = getCurrentUser();

        EmployerProfile employerProfile = employerProfileRepository.findByUserId(userId)
                        .orElseThrow(() -> new EmployerProfileNotFoundException("Employer Not Found: " + userId));

        if (!user.getUserId().equals(employerProfile.getUserId())) {
            throw new UnauthorizedException("Only own profile you can update");
        }

        EmployerMapper.updateEmployer(employerProfile, employer);

        EmployerProfile savedEmployer = employerProfileRepository.save(employerProfile);

        return EmployerMapper.toResponseDto(savedEmployer);
    }

    @Override
    public void deleteEmployerProfile(Long employerId) {

        AuthenticatedUser user = getCurrentUser();

        EmployerProfile employerProfile = employerProfileRepository.findByUserId(employerId)
                        .orElseThrow(() -> new EmployerProfileNotFoundException("Employer Not Found: " + employerId));

        // Ownership check
        if (!user.getUserId().equals(employerProfile.getUserId())) {
            throw new UnauthorizedException("Only own profile you can delete");
        }

        employerProfileRepository.delete(employerProfile);
    }
    @Override
    public void uploadProfileImage(Long userId, MultipartFile file) {

        AuthenticatedUser user = getCurrentUser();

        EmployerProfile employerProfile = employerProfileRepository.findByUserId(userId)
                        .orElseThrow(() -> new EmployerProfileNotFoundException("Employer Not Found: " + userId));

        if (!user.getUserId().equals(employerProfile.getUserId())) {
            throw new UnauthorizedException("Only own profile image can be updated");
        }

        String imageUrl = cloudinaryService.uploadEmployerProfileImage(file);

        employerProfile.setProfileImage(imageUrl);

        employerProfileRepository.save(employerProfile);
    }

    @Override
    public void uploadCoverImage(Long userId, MultipartFile file) {

        AuthenticatedUser user = getCurrentUser();

        EmployerProfile employerProfile = employerProfileRepository.findByUserId(userId)
                        .orElseThrow(() -> new EmployerProfileNotFoundException("Employer Not Found: " + userId));

        if (!user.getUserId().equals(employerProfile.getUserId())) {
            throw new UnauthorizedException(
                    "Only own cover image can be updated"
            );
        }

        String imageUrl = cloudinaryService.uploadEmployerCoverImage(file);

        employerProfile.setCoverImage(imageUrl);

        employerProfileRepository.save(employerProfile);
    }
}