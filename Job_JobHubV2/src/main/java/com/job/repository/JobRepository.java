package com.job.repository;

import com.job.entity.Job;
import com.job.entity.JobStatus;
import com.job.entity.JobType;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {

    Optional<Job> findByTitle(String title);

    Optional<Job> findBySlug(String slug);

    List<Job> findByCompanyId(Long companyId);

    List<Job> findByCategoryId(Long categoryId);

    Page<Job> findByPostedBy(Long postedBy , Pageable pageable);

    long countByPostedByAndStatus(Long postedBy, JobStatus status);

    @Query("""
    SELECT j FROM Job j
    WHERE j.postedBy = :postedBy
    AND (
        LOWER(j.title) LIKE LOWER(CONCAT('%', :search, '%'))
        OR LOWER(j.location) LIKE LOWER(CONCAT('%', :search, '%'))
    )
""")
    Page<Job> searchMyJobs(@Param("postedBy") Long postedBy, @Param("search") String search, Pageable pageable);

    List<Job> findByStatus(JobStatus status);

    List<Job> findByJobType(JobType jobType);

    List<Job> findByLocation(String location);

    List<Job> findByJobTypeAndLocation(JobType jobType, String location);

    boolean existsByCompanyIdAndTitleAndLocationAndJobTypeAndStatusIn(
            Long companyId,
            String title,
            String location,
            JobType jobType,
            List<JobStatus> statuses);

    boolean existsByCompanyIdAndTitleAndLocationAndJobTypeAndStatusInAndJobIdNot(
            Long companyId,
            String title,
            String location,
            JobType jobType,
            List<JobStatus> statuses,
            Long jobId);


    long countByCompanyId(Long companyId);

    long countByCompanyIdAndStatus(Long companyId, JobStatus status);
}