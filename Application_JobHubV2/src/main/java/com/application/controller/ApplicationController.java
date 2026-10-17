package com.application.controller;

import com.application.dto.*;
import com.application.entity.ApplicationStatus;
import com.application.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PreAuthorize("hasRole('JOB_SEEKER')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApplicationResponseDto applyApplication(@Valid @ModelAttribute ApplicationRequestDto requestDto) {

        return applicationService.applyApplication(requestDto);
    }

    @PreAuthorize("hasRole('JOB_SEEKER')")
    @GetMapping
    public List<ApplicationResponseDto> getAllApplication() {

        return applicationService.getAllApplication();
    }

    @GetMapping("/{id}")
    public ApplicationResponseDto getByApplicationId(@PathVariable("id") Long applicationId) {

        return applicationService.getByApplicationId(applicationId);
    }
    @PreAuthorize("hasRole('EMPLOYER')")
    @GetMapping("/my")
    public PageResponseDto<ApplicationResponseDto> getEmployerApplication(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String search) {

        return applicationService.getEmployerApplication(
                page,
                size,
                search
        );
    }

    @PreAuthorize("hasRole('JOB_SEEKER')")
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApplicationResponseDto updateApplication(
            @Valid @ModelAttribute ApplicationUpdateRequestDto requestDto,
            @PathVariable("id") Long applicationId) {

        return applicationService.updateApplication(requestDto, applicationId);
    }

    @PreAuthorize("hasRole('JOB_SEEKER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteApplication(@PathVariable("id") Long applicationId) {

        applicationService.deleteApplication(applicationId);

        return ResponseEntity.ok("Application Delete Successfully");
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @PutMapping("/status/{id}")
    public ApplicationResponseDto updateApplicationStatus(
            @PathVariable Long id,
            @RequestBody ApplicationStatusUpdateDto request) {

        return applicationService.updateApplicationStatus(
                id,
                request.getStatus()
        );
    }

    @GetMapping("/internal/company/{companyId}/count")
    public long countApplicationsByCompany(@PathVariable Long companyId) {
        return applicationService.countApplicationsByCompany(companyId);
    }
}