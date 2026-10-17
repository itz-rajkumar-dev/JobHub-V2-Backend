package com.profile.controller;

import com.profile.dto.EmployerResponseDto;
import com.profile.dto.EmployerUpdateRequestDto;
import com.profile.service.EmployerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/employer-profile")
public class EmployerProfileController {

    private final EmployerService employerService;

    public EmployerProfileController(EmployerService employerService) {
        this.employerService = employerService;
    }

    @GetMapping("/me")
    public EmployerResponseDto getMyProfile() {

        return employerService.getMyProfile();
    }

    @GetMapping("/user/{userId}")
    public EmployerResponseDto getProfileByUserId(@PathVariable Long userId) {

        return employerService.getProfileByUserId(userId);
    }

    @PutMapping("/update/{userId}")
    public EmployerResponseDto updateEmployerProfile( @PathVariable Long userId, @Valid @RequestBody EmployerUpdateRequestDto employer) {

        return employerService.updateEmployerProfile(employer, userId);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteEmployerProfile(@PathVariable Long userId) {

        employerService.deleteEmployerProfile(userId);

        return ResponseEntity.ok("Employer Profile Deleted Successfully");
    }

    @PutMapping("/{userId}/profile-image")
    public ResponseEntity<String> uploadProfileImage(
            @PathVariable Long userId,
            @RequestParam("profileImage") MultipartFile file) {

        employerService.uploadProfileImage(userId, file);

        return ResponseEntity.ok(
                "Employer profile image uploaded successfully"
        );
    }

    @PutMapping("/{userId}/cover-image")
    public ResponseEntity<String> uploadCoverImage(
            @PathVariable Long userId,
            @RequestParam("coverImage") MultipartFile file) {

        employerService.uploadCoverImage(userId, file);

        return ResponseEntity.ok(
                "Employer cover image uploaded successfully"
        );
    }
}