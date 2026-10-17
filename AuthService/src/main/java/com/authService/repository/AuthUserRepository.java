package com.authService.repository;

import com.authService.entity.AuthUser;
import com.authService.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {

   Optional<AuthUser> findByEmail(String email);

   List<AuthUser> findByRoleAndEnabledTrueAndUserServiceUserIdIsNotNull(Role role);
}
