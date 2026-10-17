package com.savedjob.repository;

import com.savedjob.entity.SavedJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SavedJobRepository extends JpaRepository<SavedJob,Long> {

    Optional<SavedJob> findByUserIdAndJobId(Long userId,Long jobId);

    boolean existsByUserIdAndJobId(Long userId,Long jobId);

    List<SavedJob> findByUserId(Long userId);

    void deleteByUserIdAndJobId(Long userId,Long jobId);

    long countByJobIdIn(List<Long> jobIds);

}
