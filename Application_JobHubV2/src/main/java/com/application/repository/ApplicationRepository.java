package com.application.repository;

import com.application.entity.Application;
import com.application.entity.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository
        extends JpaRepository<Application, Long> {

    // Same applicant cannot apply to same job twice
    Optional<Application> findByApplicantIdAndJobId(Long applicantId, Long jobId);

    boolean existsByApplicantIdAndJobId(Long applicantId, Long jobId);

    // Employer: all applications for a company
    List<Application> findByCompanyId(Long companyId);

    // Applicant: all applications submitted by user
    List<Application> findByApplicantId(Long applicantId);

    Page<Application> findByCompanyIdIn(List<Long> companyIds, Pageable pageable);

    long countByCompanyIdInAndStatus(List<Long> companyIds, ApplicationStatus status);

    @Query("""
    SELECT a FROM Application a
    WHERE a.companyId IN :companyIds
    AND (
        LOWER(a.applicantName) LIKE LOWER(CONCAT('%', :search, '%'))
        OR LOWER(a.applicantEmail) LIKE LOWER(CONCAT('%', :search, '%'))
    )
""")
    Page<Application> searchEmployerApplications(
            @Param("companyIds") List<Long> companyIds,
            @Param("search") String search,
            Pageable pageable
    );
    List<Application> findByJobId(Long jobId);

    long countByCompanyId(Long companyId);


}