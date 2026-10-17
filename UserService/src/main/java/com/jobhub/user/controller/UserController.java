package com.jobhub.user.controller;

import com.jobhub.user.dto.DiscoverUserResponseDto;
import com.jobhub.user.dto.UserRequestDto;
import com.jobhub.user.dto.UserResponseDto;
import com.jobhub.user.dto.UserUpdateRequestDto;
import com.jobhub.user.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDto createUser(
            @Valid @RequestBody UserRequestDto userRequestDto) {

        return userService.createUser(userRequestDto);
    }



    @PostMapping("/internal")
    public UserResponseDto createUserInternal(
            @Valid @RequestBody UserRequestDto userRequestDto) {

        return userService.createUser(userRequestDto);
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponseDto> getAllUser() {

        return userService.getAllUser();
    }


    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public UserResponseDto getUserById(
            @PathVariable("id") Long userId) {

        return userService.getUserById(userId);
    }


    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto, @PathVariable("id") Long userId) {

        userService.updateUser(userUpdateRequestDto, userId);



        return ResponseEntity.ok("User Update Successfully"
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deleteUser(
            @PathVariable("id") Long userId) {

        userService.deleteUser(userId);

        return ResponseEntity.ok(
                "User deleted successfully"
        );
    }


    @GetMapping("/email/{email}")
    @PreAuthorize("isAuthenticated()")
    public UserResponseDto getUserByEmail(
            @PathVariable("email") String email) {

        return userService.getUserByEmail(email);
    }

    @PutMapping("/{id}/profile-image")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> uploadProfileImage(
            @PathVariable("id") Long userId,
            @RequestParam("profileImage") MultipartFile file) {

        userService.uploadProfileImage(userId, file);

        return ResponseEntity.ok(
                "Profile image uploaded successfully"
        );
    }


    // =========================
    // INTERNAL GET USER
    // =========================

    @GetMapping("/internal/{id}")
    public UserResponseDto getInternalUser(
            @PathVariable Long id) {

        return userService.getInternalUserById(id);
    }


    @GetMapping("/internal/all")
    public List<UserResponseDto> getAllUsersInternal() {

        return userService.getAllUser();
    }

    @GetMapping("/discover")
    @PreAuthorize("isAuthenticated()")
    public List<DiscoverUserResponseDto> discoverAllUsers() {

        return userService.discoverAllUsers();
    }
}