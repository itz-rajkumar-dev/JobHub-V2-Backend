package com.company.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "EngagementService")
public interface EngagementClient {

    @GetMapping("/follows/internal/company/{companyId}/followers/count")
    long countCompanyFollowers(
            @PathVariable Long companyId
    );

    @GetMapping("/likes/internal/company/{companyId}/likes/count")
    long countCompanyLikes(
            @PathVariable Long companyId
    );

    @GetMapping("/comments/internal/company/{companyId}/comments/count")
    long countCompanyComments(
            @PathVariable Long companyId
    );

    @GetMapping("/likes/internal/company/{companyId}/posts/likes/count")
    long countCompanyPostLikes(
            @PathVariable Long companyId
    );

    @GetMapping("/comments/internal/company/{companyId}/posts/comments/count")
    long countCompanyPostComments(
            @PathVariable Long companyId
    );
}