package com.savedjob.serviceImpl;

import com.savedjob.client.JobClient;
import com.savedjob.dto.JobResponseDto;
import com.savedjob.dto.SavedJobRequestDto;
import com.savedjob.dto.SavedJobResponseDto;
import com.savedjob.entity.SavedJob;
import com.savedjob.exception.AlreadySavedJobExistException;
import com.savedjob.exception.SavedJobNotFoundException;
import com.savedjob.exception.UnauthorizedException;
import com.savedjob.mapper.SavedJobMapper;
import com.savedjob.repository.SavedJobRepository;
import com.savedjob.security.AuthenticatedUser;
import com.savedjob.service.SavedJobService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavedJobServiceImpl implements SavedJobService {

    private final SavedJobRepository savedJobRepository;
    private final JobClient jobClient;

    public SavedJobServiceImpl(
            SavedJobRepository savedJobRepository,
            JobClient jobClient) {

        this.savedJobRepository = savedJobRepository;
        this.jobClient = jobClient;
    }

    private AuthenticatedUser getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !(authentication.getPrincipal()
                        instanceof AuthenticatedUser)) {

            throw new UnauthorizedException("User Not Found");
        }

        return (AuthenticatedUser) authentication.getPrincipal();
    }

    @Override
    public SavedJobResponseDto saveJob(
            SavedJobRequestDto requestDto) {

        AuthenticatedUser currentUser = getCurrentUser();

        // Check whether job exists
        JobResponseDto job =
                jobClient.getByJobId(requestDto.getJobId());

        // Check duplicate save
        boolean exists =
                savedJobRepository.existsByUserIdAndJobId(
                        currentUser.getUserId(),
                        requestDto.getJobId()
                );

        if (exists) {
            throw new AlreadySavedJobExistException(
                    "Already You Saved this Job"
            );
        }

        SavedJob savedJob = SavedJobMapper.toEntity(requestDto);

        savedJob.setUserId(currentUser.getUserId());

        SavedJob saved = savedJobRepository.save(savedJob);

        return SavedJobMapper.toResponseDto(saved);
    }

    @Override
    public List<SavedJobResponseDto> getAllSavedJob() {

        AuthenticatedUser currentUser = getCurrentUser();

        return savedJobRepository
                .findByUserId(currentUser.getUserId())
                .stream()
                .map(SavedJobMapper::toResponseDto)
                .toList();
    }

    @Override
    public SavedJobResponseDto getSavedJobById(Long savedJobId) {

        AuthenticatedUser currentUser=getCurrentUser();


        SavedJob savedJob= savedJobRepository.findById(savedJobId).orElseThrow(()->new SavedJobNotFoundException("SavedJob Not Found"));

        if (!savedJob.getUserId().equals(currentUser.getUserId())) {
            throw new AccessDeniedException("You can Only View Your Own SavedJob");
        }

        return SavedJobMapper.toResponseDto(savedJob);



    }

    @Override
    public void removeSavedJob(Long savedJobId) {

        AuthenticatedUser currentUser=getCurrentUser();

        SavedJob savedJob=this.savedJobRepository.findById(savedJobId).orElseThrow(()->new SavedJobNotFoundException("savedJob Not Found"));

        if (!savedJob.getUserId().equals(currentUser.getUserId())) {
            throw new AccessDeniedException( "You can Only Delete Your Own SavedJob");
        }
        savedJobRepository.delete(savedJob);

    }

    @Override
    public long countSavedJobsByCompany(Long companyId) {

        List<Long> jobIds = jobClient.getJobIdsByCompany(companyId);

        if (jobIds == null || jobIds.isEmpty()) {
            return 0;
        }

        return savedJobRepository.countByJobIdIn(jobIds);
    }
}