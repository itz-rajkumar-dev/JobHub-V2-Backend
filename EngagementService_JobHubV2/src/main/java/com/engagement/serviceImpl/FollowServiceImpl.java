package com.engagement.serviceImpl;

import com.engagement.dto.FollowRequestDto;
import com.engagement.dto.FollowResponseDto;
import com.engagement.entity.Follow;
import com.engagement.exception.AlreadyFollowException;
import com.engagement.exception.FollowNotFoundException;
import com.engagement.exception.UnauthorizedException;
import com.engagement.mapper.FollowMapper;
import com.engagement.repository.FollowRepository;
import com.engagement.security.AuthenticatedUser;
import com.engagement.service.FollowService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowRepository followRepository;

    private AuthenticatedUser getLoggedUser() {

        return (AuthenticatedUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
    @Override
    public FollowResponseDto createFollow(FollowRequestDto requestDto) {

        AuthenticatedUser user = getLoggedUser();

        // =========================
        // User -> User
        // =========================
        if (requestDto.getFollowingUserId() != null) {

            Optional<Follow> existingFollow =
                    followRepository.findByFollowerIdAndFollowingUserId(
                            user.getUserId(),
                            requestDto.getFollowingUserId()
                    );

            // Already following -> UNFOLLOW
            if (existingFollow.isPresent()) {

                Follow follow = existingFollow.get();

                followRepository.delete(follow);

                return FollowResponseDto.builder()
                        .followingUserId(requestDto.getFollowingUserId())
                        .following(false)
                        .build();
            }
        }

        // =========================
        // User -> Company
        // =========================
        if (requestDto.getCompanyId() != null) {

            Optional<Follow> existingFollow =
                    followRepository.findByFollowerIdAndCompanyId(
                            user.getUserId(),
                            requestDto.getCompanyId()
                    );

            // Already following -> UNFOLLOW
            if (existingFollow.isPresent()) {

                Follow follow = existingFollow.get();

                followRepository.delete(follow);

                return FollowResponseDto.builder()
                        .companyId(requestDto.getCompanyId())
                        .following(false)
                        .build();
            }
        }

        // =========================
        // CREATE FOLLOW
        // =========================
        Follow follow = FollowMapper.toEntity(requestDto);

        // JWT se current user
        follow.setFollowerId(user.getUserId());

        Follow savedFollow = followRepository.save(follow);

        return FollowResponseDto.builder()
                .followId(savedFollow.getFollowId())
                .followerId(savedFollow.getFollowerId())
                .followingUserId(savedFollow.getFollowingUserId())
                .companyId(savedFollow.getCompanyId())
                .following(true)
                .build();
    }

    @Override
    public List<FollowResponseDto> getFollowersByCompany(Long companyId) {

        return this.followRepository
                .findByCompanyId(companyId)
                .stream()
                .map(FollowMapper::responseDto)
                .toList();
    }

    @Override
    public List<FollowResponseDto> getFollowersByUser(Long followingUserId) {
        return this.followRepository.findByFollowingUserId(followingUserId)
                .stream()
                .map(FollowMapper::responseDto)
                .toList();
    }

    @Override
    public List<FollowResponseDto> getFollowingByUser(Long followerId) {

        return this.followRepository.findByFollowerId(followerId)
                .stream()
                .map(FollowMapper::responseDto)
                .toList();

    }


    @Override
    public FollowResponseDto getByFollowId(Long followId) {

        Follow follow = followRepository.findById(followId)
                .orElseThrow(() -> new FollowNotFoundException("Follow Not Found: " + followId));

        return FollowMapper.responseDto(follow);
    }

    @Override
    public void deleteFollow(Long followId) {

        AuthenticatedUser user = getLoggedUser();

        Follow follow = followRepository.findById(followId)
                .orElseThrow(() -> new FollowNotFoundException("Follow Not Found: " + followId));

        if (!follow.getFollowerId().equals(user.getUserId())) {

            throw new UnauthorizedException("You are Not Authorized to Delete This Follow");
        }

        followRepository.delete(follow);
    }

    @Override
    public FollowResponseDto checkFollow(Long followingUserId) {

        AuthenticatedUser currentUser = getLoggedUser();

        Optional<Follow> follow =
                followRepository.findByFollowerIdAndFollowingUserId(currentUser.getUserId(), followingUserId);

        if (follow.isPresent()) {

            Follow f = follow.get();

            return FollowResponseDto.builder()
                    .followId(f.getFollowId())
                    .followerId(f.getFollowerId())
                    .followingUserId(f.getFollowingUserId())
                    .companyId(f.getCompanyId())
                    .following(true)
                    .build();
        }

        return FollowResponseDto.builder()
                .following(false)
                .build();
    }

    @Override
    public long countCompanyFollowers(Long companyId) {
        return followRepository.countByCompanyId(companyId);
    }
}