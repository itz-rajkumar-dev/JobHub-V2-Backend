package com.jobhub.user.serviceImpl;

import com.jobhub.user.dto.DiscoverUserResponseDto;
import com.jobhub.user.dto.UserRequestDto;
import com.jobhub.user.dto.UserResponseDto;
import com.jobhub.user.dto.UserUpdateRequestDto;
import com.jobhub.user.entity.User;
import com.jobhub.user.exception.UserAlreadyExistException;
import com.jobhub.user.exception.UserNotFoundException;
import com.jobhub.user.mapper.UserMapper;
import com.jobhub.user.repository.UserRepository;
import com.jobhub.user.security.AuthenticatedUser;
import com.jobhub.user.service.CloudinaryService;
import com.jobhub.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

      private AuthenticatedUser getLoggedUser(){

          return (AuthenticatedUser) SecurityContextHolder
                  .getContext().getAuthentication()
                  .getPrincipal();
      }
    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {

        if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {

            throw new UserAlreadyExistException("Email Already Exist: " + userRequestDto.getEmail());
        }

        User user = UserMapper.toEntity(userRequestDto);

        User savedUser = userRepository.save(user);

        return UserMapper.toResponseDto(savedUser);
    }


    @Override
    public List<UserResponseDto> getAllUser() {

        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponseDto)
                .toList();
    }


    @Override
    public UserResponseDto getUserById(Long userId) {

        AuthenticatedUser currentUser = getLoggedUser();

        if (!"ADMIN".equals(currentUser.getRole())
                && !currentUser.getUserId().equals(userId)) {

            throw new AccessDeniedException("You can only view your own account");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Not Found: " + userId));

        return UserMapper.toResponseDto(user);
    }



    @Override
    public UserResponseDto updateUser(
            UserUpdateRequestDto dto,
            Long userId) {

        AuthenticatedUser currentUser = getLoggedUser();


        if (!"ADMIN".equals(currentUser.getRole())
                && !currentUser.getUserId().equals(userId)) {

            throw new AccessDeniedException(
                    "You can only update your own account");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Not Found: " + userId));

        // Update fullname only if provided
        if (dto.getFullname() != null
                && !dto.getFullname().isBlank()) {

            user.setFullname(dto.getFullname());
        }

        // Update email only if provided
        if (dto.getEmail() != null
                && !dto.getEmail().isBlank()) {

            if (!user.getEmail().equalsIgnoreCase(dto.getEmail())
                    && userRepository.findByEmail(dto.getEmail())
                    .isPresent()) {

                throw new UserAlreadyExistException(
                        "Email Already Exist: " + dto.getEmail());
            }

            user.setEmail(dto.getEmail());
        }

        // Update phone only if provided
        if (dto.getPhone() != null
                && !dto.getPhone().isBlank()) {

            user.setPhone(dto.getPhone());
        }

        // Update location only if provided
        if (dto.getLocation() != null
                && !dto.getLocation().isBlank()) {

            user.setLocation(dto.getLocation());
        }

        return UserMapper.toResponseDto(
                userRepository.save(user));
    }


    @Override
    public void deleteUser(Long userId) {

        AuthenticatedUser currentUser = getLoggedUser();

        if (!"ADMIN".equals(currentUser.getRole())
                && !currentUser.getUserId().equals(userId)) {

            throw new AccessDeniedException(
                    "You can only delete your own account");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Not Found: " + userId));

        userRepository.delete(user);
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        return UserMapper.toResponseDto(user);
    }

    @Override
    public void uploadProfileImage(Long userId, MultipartFile file) {

        AuthenticatedUser currentUser = getLoggedUser();

        if (!currentUser.getUserId().equals(userId)) {

            throw new AccessDeniedException(
                    "You can only update your own profile image");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Not Found: " + userId));

        String imageUrl = cloudinaryService.uploadProfileImage(file);

        user.setProfileImage(imageUrl);

        userRepository.save(user);
    }

    @Override
    public List<DiscoverUserResponseDto> discoverAllUsers() {


        return userRepository.findAll()
                .stream()
                .map(user -> new DiscoverUserResponseDto(
                        user.getUserId(),
                        user.getFullname(),
                        user.getProfileImage(),
                        user.getLocation()
                ))
                .toList();
    }

    @Override
    public UserResponseDto getInternalUserById(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Not Found: " + userId));

        return UserMapper.toResponseDto(user);
    }
}