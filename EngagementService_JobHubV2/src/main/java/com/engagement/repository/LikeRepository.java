package com.engagement.repository;

import com.engagement.entity.Like;
import com.engagement.entity.TargetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserIdAndTargetIdAndTargetType(
            Long userId,
            Long targetId,
            TargetType targetType
    );

    Optional<Like> findByUserIdAndTargetIdAndTargetType(
            Long userId,
            Long targetId,
            TargetType targetType
    );
    List<Like> findByTargetIdAndTargetType(
            Long targetId,
            TargetType targetType
    );


    long countByTargetIdAndTargetType(
            Long targetId,
            TargetType targetType
    );

    long countByTargetIdInAndTargetType(
            List<Long> targetIds,
            TargetType targetType
    );
}