package com.profile.serviceImpl;

import com.profile.client.UserClient;
import com.profile.dto.ProfileRequestDto;
import com.profile.dto.ProfileResponseDto;
import com.profile.entity.EmployerProfile;
import com.profile.entity.ExperienceLevel;
import com.profile.entity.Profile;
import com.profile.exception.ProfileAlreadyExistException;
import com.profile.exception.ProfileNotFoundException;
import com.profile.exception.UnauthorizedException;
import com.profile.mapper.ProfileMapper;
import com.profile.repository.EmployerProfileRepository;
import com.profile.repository.ProfileRepository;
import com.profile.security.AuthenticatedUser;
import com.profile.service.CloudinaryService;
import com.profile.service.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {


    private final EmployerProfileRepository employerProfileRepository;
    private final ProfileRepository profileRepository;
    private final UserClient userClient;
    private  final CloudinaryService cloudinaryService;

    public ProfileServiceImpl(
            ProfileRepository profileRepository,
            UserClient userClient,
            CloudinaryService cloudinaryService,
            EmployerProfileRepository employerProfileRepository

    ) {

        this.profileRepository = profileRepository;
        this.userClient = userClient;
        this.cloudinaryService=cloudinaryService;
        this.employerProfileRepository = employerProfileRepository;
    }

    private AuthenticatedUser getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof AuthenticatedUser)) {

            throw new UnauthorizedException("User Not Found");
        }

        return (AuthenticatedUser)
                authentication.getPrincipal();
    }


    @Override
    public ProfileResponseDto createProfile(
            ProfileRequestDto requestDto) {

        AuthenticatedUser currentUser = getCurrentUser();


         userClient.getByUserId(currentUser.getUserId());

        // Check profile already exists
        boolean exist = profileRepository.existsByUserId(currentUser.getUserId()
                );

        if (exist) {throw new ProfileAlreadyExistException("Profile Already Exist"
            );
        }

        Profile profile = ProfileMapper.toEntity(requestDto);

        // userId comes from JWT
        profile.setUserId(currentUser.getUserId());

        Profile savedProfile = profileRepository.save(profile);

        return ProfileMapper.toResponseDto(savedProfile);
    }


    @Override
    public ProfileResponseDto getMyProfile() {

        AuthenticatedUser currentUser = getCurrentUser();

        Profile profile = profileRepository.findByUserId(
                        currentUser.getUserId()).orElseThrow(() -> new ProfileNotFoundException("Profile Not Found")
                );

        return ProfileMapper.toResponseDto(profile);
    }


    @Override
    public ProfileResponseDto updateProfile(
            ProfileRequestDto requestDto,
            Long profileId) {

        AuthenticatedUser currentUser = getCurrentUser();

        Profile profile = profileRepository.findById(profileId)
                        .orElseThrow(() -> new ProfileNotFoundException("Profile Not Found: " + profileId)
                        );

        if (!profile.getUserId().equals(currentUser.getUserId())) {

            throw new AccessDeniedException("You can only update your own profile");
        }

        ProfileMapper.updateEntity(requestDto, profile);

        Profile updatedProfile = profileRepository.save(profile);

        return ProfileMapper.toResponseDto(updatedProfile);
    }


    @Override
    public void deleteProfile(Long profileId) {

        AuthenticatedUser currentUser = getCurrentUser();

        Profile profile = profileRepository.findById(profileId)
                        .orElseThrow(() -> new ProfileNotFoundException("Profile Not Found: " + profileId));


        if (!profile.getUserId().equals(currentUser.getUserId())) {

            throw new AccessDeniedException("You can only delete your own profile");
        }

        profileRepository.delete(profile);
    }


    @Override
    public List<ProfileResponseDto> getAllProfiles() {

        AuthenticatedUser currentUser = getCurrentUser();

        if (!"ADMIN".equals(currentUser.getRole())) {

            throw new AccessDeniedException("Only Admin can view all profiles");
        }

        return profileRepository.findAll()
                .stream()
                .map(ProfileMapper::toResponseDto)
                .toList();
    }


    @Override
    public ProfileResponseDto getProfileById(Long profileId) {

        AuthenticatedUser currentUser = getCurrentUser();

        Profile profile = profileRepository.findById(profileId)
                        .orElseThrow(() -> new ProfileNotFoundException("Profile Not Found: " + profileId)
                        );

        // Admin can view any profile
        if ("ADMIN".equals(currentUser.getRole())) {
            return ProfileMapper.toResponseDto(profile);
        }

        // Job seeker can view only own profile
        if (!profile.getUserId().equals(currentUser.getUserId())) {

            throw new AccessDeniedException("You can only view your own profile"
            );
        }

        return ProfileMapper.toResponseDto(profile);
    }

    @Override
    public void uploadCoverImage(Long profileId, MultipartFile file) {

        AuthenticatedUser currentUser = getCurrentUser();

        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ProfileNotFoundException("Profile Not Found: " + profileId));

        if (!profile.getUserId().equals(currentUser.getUserId())) {

            throw new AccessDeniedException("You can only update your own profile");
        }

        String imageUrl = cloudinaryService.uploadCoverImage(file);

        profile.setCoverImage(imageUrl);

        profileRepository.save(profile);
    }

    @Override
    public ProfileResponseDto getProfileByUserId(Long userId) {

        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found for user: " + userId));

        return ProfileMapper.toResponseDto(profile);
    }

    @Override
    public void createProfileForUser(Long userId, String role) {

        if ("JOB_SEEKER".equals(role)) {

            Profile profile = new Profile();

            profile.setUserId(userId);
            profile.setSkills(new ArrayList<>());
            profile.setTotalExperienceYears(0);
            profile.setExperienceLevel(ExperienceLevel.FRESHER);

            profileRepository.save(profile);

        } else if ("EMPLOYER".equals(role)) {

            EmployerProfile employerProfile = new EmployerProfile();

            employerProfile.setUserId(userId);

            employerProfileRepository.save(employerProfile);
        }
    }

}