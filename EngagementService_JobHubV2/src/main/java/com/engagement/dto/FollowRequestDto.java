package com.engagement.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowRequestDto {

    private Long followingUserId;

    private Long companyId;

    @AssertTrue(message = "Either followingUserId or companyId must be provided")
    public boolean isValidTarget() {
        return (followingUserId != null && companyId == null)
                || (followingUserId == null && companyId != null);
    }
}