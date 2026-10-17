package com.engagement.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowResponseDto {

    private Long followId;
    private Long followerId;
    private Long followingUserId;
    private Long companyId;
    private Boolean following;
}
