package com.jobhub.user.service;

import com.jobhub.user.dto.DiscoverUserResponseDto;
import com.jobhub.user.dto.UserRequestDto;
import com.jobhub.user.dto.UserResponseDto;
import com.jobhub.user.dto.UserUpdateRequestDto;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface UserService {

    UserResponseDto createUser(UserRequestDto userRequestDto);

    List<UserResponseDto> getAllUser();

    UserResponseDto getUserById(Long userId);
    UserResponseDto getInternalUserById(Long userId);

    UserResponseDto updateUser(UserUpdateRequestDto userRequestDto, Long userId);

    void deleteUser(Long userId);

    UserResponseDto getUserByEmail(String email);

    void uploadProfileImage(Long userId, MultipartFile file);

    List<DiscoverUserResponseDto> discoverAllUsers();
}
