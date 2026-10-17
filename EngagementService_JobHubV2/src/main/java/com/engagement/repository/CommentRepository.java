package com.engagement.repository;

import com.engagement.entity.Comment;
import com.engagement.entity.TargetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByTargetIdAndTargetType(
            Long targetId,
            TargetType targetType
    );

    long countByTargetIdAndTargetType(Long targetId, TargetType targetType);

    long countByTargetIdInAndTargetType(
            List<Long> targetIds,
            TargetType targetType
    );
}