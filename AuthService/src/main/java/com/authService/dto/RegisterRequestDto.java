package com.authService.dto;

import com.authService.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDto {

    @NotBlank(message = "FullName is required")
    private String fullname;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Registration token is required")
    private String registrationToken;

    private Role role;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Phone is required")
    @Pattern(
            regexp = "[6-9]\\d{9}$",
            message = "Invalid Mobile Number"
    )
    private String phone;

    private String profileImage;

    @NotBlank(message = "Location is required")
    private String location;
}