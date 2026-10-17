package com.engagement.service;

import com.engagement.dto.LikeRequestDto;
import com.engagement.dto.LikeResponseDto;
import com.engagement.entity.TargetType;

import java.util.List;

public interface LikeService {

    LikeResponseDto createLike(LikeRequestDto dto);

    List<LikeResponseDto> getAllLike(
            Long targetId,
            TargetType targetType
    );

    LikeResponseDto getByLikeId(Long likeId);

    String deleteLike(Long likeId);

    long countCompanyLikes(Long companyId);

    long countCompanyPostLikes(Long companyId);
}
