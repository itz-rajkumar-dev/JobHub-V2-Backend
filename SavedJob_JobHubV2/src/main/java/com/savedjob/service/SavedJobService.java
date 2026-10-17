package com.savedjob.service;

import com.savedjob.dto.SavedJobRequestDto;
import com.savedjob.dto.SavedJobResponseDto;

import java.util.List;

public interface SavedJobService {

    SavedJobResponseDto saveJob(SavedJobRequestDto requestDto);

    List<SavedJobResponseDto> getAllSavedJob();

    SavedJobResponseDto getSavedJobById(Long savedJobId);

    void removeSavedJob(Long savedJobId);

    long countSavedJobsByCompany(Long companyId);
}
