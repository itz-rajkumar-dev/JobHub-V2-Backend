package com.engagement.mapper;

import com.engagement.dto.FollowRequestDto;
import com.engagement.dto.FollowResponseDto;
import com.engagement.entity.Follow;

public class FollowMapper {

    public static Follow toEntity(FollowRequestDto dto) {

        Follow follow = new Follow();

        follow.setFollowingUserId(dto.getFollowingUserId());
        follow.setCompanyId(dto.getCompanyId());

        return follow;
    }

    public static FollowResponseDto responseDto(Follow follow) {

        FollowResponseDto dto = new FollowResponseDto();

        dto.setFollowId(follow.getFollowId());
        dto.setFollowerId(follow.getFollowerId());
        dto.setFollowingUserId(follow.getFollowingUserId());
        dto.setCompanyId(follow.getCompanyId());

        return dto;
    }
}