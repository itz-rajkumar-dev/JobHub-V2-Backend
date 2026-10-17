package com.authService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequestDto {

    private String fullname;
    private String email;
    private String phone;
    private String profileImage;
    private String location;

}