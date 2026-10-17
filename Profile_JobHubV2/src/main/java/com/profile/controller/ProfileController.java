package com.profile.controller;

import com.profile.dto.ProfileRequestDto;
import com.profile.dto.ProfileResponseDto;
import com.profile.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    @PreAuthorize("hasRole('JOB_SEEKER')")
    public ProfileResponseDto createProfile(@Valid @RequestBody ProfileRequestDto requestDto) {

        return profileService.createProfile(requestDto);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('JOB_SEEKER')")
    public ProfileResponseDto getMyProfile() {

        return profileService.getMyProfile();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('JOB_SEEKER')")
    public ResponseEntity<String> updateProfile(@Valid @RequestBody ProfileRequestDto requestDto, @PathVariable("id") Long profileId) {

        profileService.updateProfile(requestDto, profileId);

        return ResponseEntity.ok("Profile Update Successfully");
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('JOB_SEEKER')")
    public ResponseEntity<String> deleteProfile(@PathVariable("id") Long profileId) {

        profileService.deleteProfile(profileId);

        return ResponseEntity.ok("Profile Delete Successfully");
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProfileResponseDto getByProfileId(@PathVariable("id") Long profileId) {

        return profileService.getProfileById(profileId);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<ProfileResponseDto> getAllProfile() {

        return profileService.getAllProfiles();
    }

    @PutMapping("/{id}/cover-image")
    @PreAuthorize("hasRole('JOB_SEEKER')")
    public ResponseEntity<String> uploadCoverImage(
            @PathVariable Long id,
            @RequestParam("coverImage") MultipartFile file) {

        profileService.uploadCoverImage(id, file);

        return ResponseEntity.ok("Cover image uploaded successfully");
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("permitAll()")
    public ProfileResponseDto getProfileByUserId(
            @PathVariable Long userId) {

        return profileService.getProfileByUserId(userId);
    }

    @PostMapping("/internal")
    public ResponseEntity<Void> createProfileInternal(@RequestParam Long userId ,@RequestParam String role) {

        profileService.createProfileForUser(userId,role);

        return ResponseEntity.ok().build();
    }
}