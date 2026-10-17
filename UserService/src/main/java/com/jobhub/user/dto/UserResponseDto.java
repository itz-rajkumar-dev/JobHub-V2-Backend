package com.jobhub.user.dto;

import com.jobhub.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private Long userId;
    private String fullname;
    private String email;
    private String phone;
    private String profileImage;
    private String location;
    private boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}