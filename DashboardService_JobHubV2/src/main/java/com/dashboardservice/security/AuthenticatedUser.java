package com.dashboardservice.security;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticatedUser {

    private Long userId;
    private String email;
    private String role;

}
