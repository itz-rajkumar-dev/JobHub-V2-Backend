package com.engagement.repository;

import com.engagement.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByFollowerIdAndCompanyId(Long followerId, Long companyId);

    boolean existsByFollowerIdAndFollowingUserId(Long followerId, Long followingUserId);

    Optional<Follow> findByFollowerIdAndCompanyId(Long followerId, Long companyId);

    List<Follow> findByCompanyId(Long companyId);

    List<Follow> findByFollowingUserId(Long followingUserId);

    List<Follow> findByFollowerId(Long followerId);

    Optional<Follow> findByFollowerIdAndFollowingUserId(Long followerId, Long followingUserId);

    long countByCompanyId(Long companyId);
}