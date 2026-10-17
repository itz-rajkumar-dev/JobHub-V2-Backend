package com.company.repository;

import com.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByCompanyName(String companyName);

    Optional<Company> findByRegistrationNumber(String registrationNumber);

    List<Company> findByOwnerId(Long ownerId);

    long countByOwnerId(Long ownerId);

    @Modifying
    @Query("""
    UPDATE Company c
    SET c.profileViews = c.profileViews + 1
    WHERE c.companyId = :companyId
""")
    int incrementProfileViews(@Param("companyId") Long companyId);

    @Query("""
    SELECT COALESCE(SUM(c.profileViews), 0)
    FROM Company c
    WHERE c.ownerId = :ownerId
""")
    long countProfileViewsByOwnerId(@Param("ownerId") Long ownerId);
}