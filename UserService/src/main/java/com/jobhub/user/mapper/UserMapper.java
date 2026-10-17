package com.jobhub.user.mapper;

import com.jobhub.user.dto.UserRequestDto;
import com.jobhub.user.dto.UserResponseDto;
import com.jobhub.user.dto.UserUpdateRequestDto;
import com.jobhub.user.entity.Role;
import com.jobhub.user.entity.User;

public class UserMapper {

    public static User toEntity(UserRequestDto dto) {

        User user = new User();

        user.setFullname(dto.getFullname());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setProfileImage(dto.getProfileImage());
        user.setLocation(dto.getLocation());


        return user;
    }

    public static UserResponseDto toResponseDto(User user) {

        UserResponseDto dto = new UserResponseDto();

        dto.setUserId(user.getUserId());
        dto.setFullname(user.getFullname());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setProfileImage(user.getProfileImage());
        dto.setLocation(user.getLocation());
        dto.setEnabled(user.isEnabled());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());


        return dto;
    }



}