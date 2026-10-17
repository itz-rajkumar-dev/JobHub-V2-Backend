package com.engagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private Long userId;

    private Long targetId;

    @Enumerated(EnumType.STRING)
    private TargetType targetType;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
}