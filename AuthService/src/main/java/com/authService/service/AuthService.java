package com.authService.service;

import com.authService.dto.LoginRequestDto;
import com.authService.dto.LoginResponseDto;
import com.authService.dto.RegisterRequestDto;

import java.util.List;

public interface AuthService {

    LoginResponseDto login(LoginRequestDto request);


    void register(RegisterRequestDto request);

    List<Long> getJobSeekerUserIds();
}
