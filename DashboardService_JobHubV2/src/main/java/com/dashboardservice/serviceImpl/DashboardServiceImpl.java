package com.dashboardservice.serviceImpl;

import com.dashboardservice.client.ApplicationClient;
import com.dashboardservice.client.CompanyClient;
import com.dashboardservice.client.JobClient;
import com.dashboardservice.dto.CompanyResponseDto;
import com.dashboardservice.dto.DashboardResponseDto;
import com.dashboardservice.enums.JobStatus;
import com.dashboardservice.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private CompanyClient companyClient;

    @Autowired
    private JobClient jobClient;

    @Autowired
    private ApplicationClient applicationClient;

    @Override
    public DashboardResponseDto getEmployerDashboard() {

        // later JWT se actual userId lenge
        Long ownerId = 1L;

        // Employer ki companies
        List<CompanyResponseDto> companies =
                companyClient.getCompaniesByOwnerId(ownerId);

        // Total companies
        Long totalCompanies =
                companyClient.countCompaniesByOwner(ownerId);

        // Active jobs
        Long activeJobs = companies.stream().mapToLong(company ->
                        jobClient.countJobsByCompanyAndStatus(
                                company.getCompanyId(),
                                JobStatus.OPEN

                        ))
                .sum();

        // Total applications
        Long totalApplications = companies.stream()
                .mapToLong(company ->
                        applicationClient.countApplicationsByCompany(
                                company.getCompanyId()
                        ))
                .sum();

        // Profile views
        Long profileViews = companyClient
                .countProfileViewsByOwner(ownerId);

        return DashboardResponseDto.builder()
                .totalCompanies(totalCompanies)
                .activeJobs(activeJobs)
                .totalApplications(totalApplications)
                .profileViews(profileViews)
                .build();
    }
}