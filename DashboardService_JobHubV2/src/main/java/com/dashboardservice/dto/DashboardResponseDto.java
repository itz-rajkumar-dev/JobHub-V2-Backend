package com.dashboardservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardResponseDto {

    private Long totalCompanies;

    private Long activeJobs;

    private Long totalApplications;

    private Long profileViews;
}

