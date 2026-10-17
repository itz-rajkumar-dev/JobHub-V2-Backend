package com.jobhub.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequestDto {

    @Size(max = 100, message = "Full name is too long")
    private String fullname;

    @Email(message = "Invalid email address")
    private String email;

    @Pattern(
            regexp = "[6-9]\\d{9}",
            message = "Invalid Mobile Number"
    )
    private String phone;

    private String location;



}
