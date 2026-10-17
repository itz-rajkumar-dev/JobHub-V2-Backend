package com.company.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyDeleteCheckResponseDto {

    private Long companyId;
    private String companyName;

    private boolean canDelete;

    private long jobs;
    private long applications;
    private long savedJobs;

    private long posts;
    private long companyLifePosts;

    private long followers;
    private long likes;
    private long comments;

    private long postLikes;
    private long postComments;
}