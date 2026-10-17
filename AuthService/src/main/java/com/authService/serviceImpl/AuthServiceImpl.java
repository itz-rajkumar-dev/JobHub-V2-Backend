package com.authService.serviceImpl;

import com.authService.client.ProfileServiceClient;
import com.authService.client.UserServiceClient;
import com.authService.dto.*;
import com.authService.entity.AuthUser;
import com.authService.entity.Role;
import com.authService.exception.UserAlreadyExistException;
import com.authService.exception.UserNotFoundException;
import com.authService.repository.AuthUserRepository;
import com.authService.security.JwtService;
import com.authService.service.AuthService;

import com.authService.service.OtpVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private ProfileServiceClient profileServiceClient;

    @Autowired
    private OtpVerificationService otpVerificationService;

    // LOGIN
    @Override
    public LoginResponseDto login(LoginRequestDto request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        AuthUser user = authUserRepository
                .findByEmail(request.getEmail()).orElseThrow(() -> new UserNotFoundException("User Not Found"));

        String token = jwtService.generateToken(user);

        return new LoginResponseDto(
                token,
                user.getUserServiceUserId(),
                user.getRole().name()
                );
    }


    // REGISTER
    @Override
    public void register(RegisterRequestDto request) {


        String email = request.getEmail().trim().toLowerCase();
        // 1. Check email already exists in Auth DB
        if (authUserRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistException(
                    "Email Already Exists: " + email
            );
        }

        // 2. Create AuthUser
        AuthUser authUser = new AuthUser();

        authUser.setEmail(email);

        authUser.setPassword(passwordEncoder.encode(request.getPassword()));

        otpVerificationService.validateRegistrationToken(email, request.getRegistrationToken());

        if (request.getRole() == null ||
                (request.getRole() != Role.JOB_SEEKER &&
                        request.getRole() != Role.EMPLOYER)) {
            throw new IllegalArgumentException("Invalid registration role");
        }
        // Role request se aayega
        authUser.setRole(request.getRole());

        authUser.setEnabled(true);

        // 3. Save AuthUser
        AuthUser savedAuthUser = authUserRepository.save(authUser);

        // 4. Prepare User Service data
        UserCreateRequestDto userRequest = new UserCreateRequestDto();

        userRequest.setFullname(request.getFullname());
        userRequest.setEmail(email);
        userRequest.setPhone(request.getPhone());
        userRequest.setProfileImage(request.getProfileImage());
        userRequest.setLocation(request.getLocation());


        // 5. Create User + Profile
        try {



            UserCreateResponseDto createdUser =
                    userServiceClient.createUser(userRequest);

            Long userServiceUserId = createdUser.getUserId();

            // Save UserService ID inside AuthUser
            savedAuthUser.setUserServiceUserId(userServiceUserId);
            authUserRepository.save(savedAuthUser);

            // 6. Role based profile creation
            profileServiceClient.createProfile(
                    userServiceUserId,
                    savedAuthUser.getRole().name()
            );

            // Consume token after successful profile creation
            otpVerificationService.consumeRegistrationToken(
                    email,
                    request.getRegistrationToken()
            );
        } catch (Exception ex) {
            try {
                authUserRepository.delete(savedAuthUser);
            } catch (Exception rollbackEx) {
                ex.addSuppressed(rollbackEx);
            }

            throw ex;
        }
    }


    @Override
    public List<Long> getJobSeekerUserIds() {

        return authUserRepository
                .findByRoleAndEnabledTrueAndUserServiceUserIdIsNotNull(Role.JOB_SEEKER)
                .stream()
                .map(AuthUser::getUserServiceUserId)
                .toList();
    }
}