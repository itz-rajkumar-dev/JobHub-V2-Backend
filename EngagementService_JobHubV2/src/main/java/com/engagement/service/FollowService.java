package com.engagement.service;

import com.engagement.dto.FollowRequestDto;
import com.engagement.dto.FollowResponseDto;

import java.util.List;

public interface FollowService {

    FollowResponseDto createFollow(FollowRequestDto requestDto);

    List<FollowResponseDto> getFollowersByCompany(Long companyId);

    List<FollowResponseDto> getFollowersByUser(Long followingUserId);

    List<FollowResponseDto> getFollowingByUser(Long followerId);

    FollowResponseDto getByFollowId(Long followId);

    void deleteFollow(Long followId);

    FollowResponseDto checkFollow(Long followingUserId);

    long countCompanyFollowers(Long companyId);
}