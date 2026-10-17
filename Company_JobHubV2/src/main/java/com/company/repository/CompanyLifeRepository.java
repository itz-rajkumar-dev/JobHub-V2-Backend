package com.company.repository;

import com.company.entity.CompanyLife;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyLifeRepository
        extends JpaRepository<CompanyLife, Long> {

    List<CompanyLife> findByCompanyIdOrderByDisplayOrderAsc(Long companyId);

    long countByCompanyId(Long companyId);
}