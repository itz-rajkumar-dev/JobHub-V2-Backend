package com.company.repository;

import com.company.entity.CompanyPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyPostRepository extends JpaRepository<CompanyPost ,Long> {

    List<CompanyPost> findByCompanyId(Long companyId);
    long countByCompanyId(Long companyId);
}
