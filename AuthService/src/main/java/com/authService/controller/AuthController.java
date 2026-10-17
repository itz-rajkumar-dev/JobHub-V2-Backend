package com.authService.controller;

import com.authService.dto.LoginRequestDto;
import com.authService.dto.LoginResponseDto;
import com.authService.dto.RegisterRequestDto;
import com.authService.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public LoginResponseDto login(
            @Valid @RequestBody LoginRequestDto request) {

        return authService.login(request);
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequestDto request) {

        authService.register(request);

        return ResponseEntity.ok("User Registered Successfully");
    }

    @GetMapping("/internal/job-seeker-ids")
    public List<Long> getJobSeekerUserIds() {
        return authService.getJobSeekerUserIds();
    }

}