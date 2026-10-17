package com.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {

    private Long userId;
    private String fullname;
    private String phone;
    private String email;
    private String profileImage;
}