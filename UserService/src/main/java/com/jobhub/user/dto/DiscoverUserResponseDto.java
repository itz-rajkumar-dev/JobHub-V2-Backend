package com.jobhub.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscoverUserResponseDto {

    private Long userId;
    private String fullname;
    private String profileImage;
    private String location;
}
