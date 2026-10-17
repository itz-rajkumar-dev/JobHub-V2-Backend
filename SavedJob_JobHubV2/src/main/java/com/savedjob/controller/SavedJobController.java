package com.savedjob.controller;

import com.savedjob.dto.SavedJobRequestDto;
import com.savedjob.dto.SavedJobResponseDto;
import com.savedjob.service.SavedJobService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/saved-job")
public class SavedJobController {

    private final SavedJobService savedJobService;

    public SavedJobController(SavedJobService savedJobService) {
        this.savedJobService = savedJobService;
    }

    @PostMapping
    @PreAuthorize("hasRole('JOB_SEEKER')")
    public SavedJobResponseDto saveJob(@Valid @RequestBody SavedJobRequestDto requestDto) {

        return savedJobService.saveJob(requestDto);
    }

    @GetMapping
    @PreAuthorize("hasRole('JOB_SEEKER')")
    public List<SavedJobResponseDto> getAllSavedJob() {

        return savedJobService.getAllSavedJob();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('JOB_SEEKER')")
    public SavedJobResponseDto getBySavedJobId(@PathVariable("id") Long savedJobId) {

        return savedJobService.getSavedJobById(savedJobId);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('JOB_SEEKER')")
    public ResponseEntity<String> removeSavedJob(@PathVariable("id") Long savedJobId) {

        savedJobService.removeSavedJob(savedJobId);

        return ResponseEntity.ok("Saved Job Removed Successfully");
    }

    @GetMapping("/internal/company/{companyId}/count")
    public long countSavedJobsByCompany(
            @PathVariable Long companyId) {

        return savedJobService.countSavedJobsByCompany(companyId);
    }
}