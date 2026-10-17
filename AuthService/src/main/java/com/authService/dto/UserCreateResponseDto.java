package com.authService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateResponseDto {

    private Long userId;
    private String fullname;
    private String email;
    private String phone;
    private String profileImage;
    private String location;
    private boolean enabled;
}