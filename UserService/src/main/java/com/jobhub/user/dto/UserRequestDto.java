package com.jobhub.user.dto;

import com.jobhub.user.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "FullName is required")
    private String fullname;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    private String email;


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