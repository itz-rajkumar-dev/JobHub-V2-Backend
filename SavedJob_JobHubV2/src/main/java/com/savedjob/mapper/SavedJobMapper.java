package com.savedjob.mapper;

import com.savedjob.dto.SavedJobRequestDto;
import com.savedjob.dto.SavedJobResponseDto;
import com.savedjob.entity.SavedJob;

public class SavedJobMapper {

    public static SavedJob toEntity(SavedJobRequestDto requestDto) {

        SavedJob savedJob = new SavedJob();
        savedJob.setJobId(requestDto.getJobId());

        return savedJob;
    }

    public static SavedJobResponseDto toResponseDto(SavedJob savedJob) {

        SavedJobResponseDto responseDto = new SavedJobResponseDto();

        responseDto.setSavedJobId(savedJob.getSavedJobId());
        responseDto.setUserId(savedJob.getUserId());
        responseDto.setJobId(savedJob.getJobId());
        responseDto.setSavedAt(savedJob.getSavedAt());

        return responseDto;
    }
}