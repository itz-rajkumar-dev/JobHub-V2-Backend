package com.engagement.serviceImpl;

import com.engagement.client.CompanyPostClient;
import com.engagement.dto.LikeRequestDto;
import com.engagement.dto.LikeResponseDto;
import com.engagement.entity.Like;
import com.engagement.entity.TargetType;
import com.engagement.exception.LikeNotFoundException;
import com.engagement.exception.UnauthorizedException;
import com.engagement.mapper.LikeMapper;
import com.engagement.repository.LikeRepository;
import com.engagement.security.AuthenticatedUser;
import com.engagement.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private CompanyPostClient companyPostClient;

   private AuthenticatedUser getLoggedUser(){

        return (AuthenticatedUser) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
    }
    @Override
    public LikeResponseDto createLike(LikeRequestDto dto) {

        AuthenticatedUser user = getLoggedUser();

        Optional<Like> existingLike =
                likeRepository.findByUserIdAndTargetIdAndTargetType(
                        user.getUserId(),
                        dto.getTargetId(),
                        dto.getTargetType()
                );

        // Already liked → UNLIKE
        if (existingLike.isPresent()) {

            Like like = existingLike.get();

            likeRepository.delete(like);

            LikeResponseDto response = new LikeResponseDto();

            response.setUserId(user.getUserId());
            response.setTargetId(dto.getTargetId());
            response.setTargetType(dto.getTargetType());
            response.setLiked(false);
            response.setMessage("You unliked this post");

            return response;
        }

        // Not liked → LIKE
        Like like = LikeMapper.toEntity(dto);

        like.setUserId(user.getUserId());

        Like savedLike = likeRepository.save(like);

        LikeResponseDto response =
                LikeMapper.toResponseDto(savedLike);

        response.setLiked(true);
        response.setMessage("You liked this post");

        return response;
    }


    @Override
    public List<LikeResponseDto> getAllLike(
            Long targetId,
            TargetType targetType) {

        return this.likeRepository
                .findByTargetIdAndTargetType(targetId, targetType)
                .stream()
                .map(LikeMapper::toResponseDto)
                .toList();
    }

    @Override
    public LikeResponseDto getByLikeId(Long likeId) {

        Like like = likeRepository.findById(likeId)
                .orElseThrow(() -> new LikeNotFoundException("Like not found: " + likeId));

        return LikeMapper.toResponseDto(like);
    }
    @Override
    public String deleteLike(Long likeId) {

        Like like = likeRepository.findById(likeId)
                .orElseThrow(() ->
                        new LikeNotFoundException("Like not found: " + likeId));

        AuthenticatedUser user = getLoggedUser();

        if (!like.getUserId().equals(user.getUserId())) {

            throw new UnauthorizedException(
                    "You are not authorized to delete this like: " + likeId);
        }

        likeRepository.delete(like);

        return "You unliked this post";
    }

    @Override
    public long countCompanyLikes(Long companyId) {

        return likeRepository.countByTargetIdAndTargetType(companyId, TargetType.COMPANY);
    }

    @Override
    public long countCompanyPostLikes(Long companyId) {

        List<Long> postIds = companyPostClient.getPostIdsByCompany(companyId);

        if (postIds == null || postIds.isEmpty()) {
            return 0;
        }

        return likeRepository.countByTargetIdInAndTargetType(
                postIds,
                TargetType.POST
        );
    }
}
