package com.company.serviceImpl;

import com.company.cloudinary.CloudinaryService;
import com.company.dto.CompanyPostRequestDto;
import com.company.dto.CompanyPostResponseDto;
import com.company.dto.CompanyUpdatePostRequestDto;
import com.company.entity.Company;
import com.company.entity.CompanyPost;
import com.company.exception.CompanyNotFoundException;
import com.company.exception.CompanyPostNotFoundException;
import com.company.exception.UnauthorizedException;
import com.company.mapper.CompanyMapper;
import com.company.repository.CompanyPostRepository;
import com.company.repository.CompanyRepository;
import com.company.security.AuthenticatedUser;
import com.company.service.CompanyPostService;
import com.company.storage.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyPostServiceImpl implements CompanyPostService {

    @Autowired
    private CompanyPostRepository companyPostRepository;
     @Autowired
     private CompanyRepository companyRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    // Logged-in user
    private AuthenticatedUser getLoggedInUser() {

        return (AuthenticatedUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
    @Override
    public CompanyPostResponseDto createPost(CompanyPostRequestDto request) {

        AuthenticatedUser user = getLoggedInUser();

        // Check company exists
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new CompanyNotFoundException("Company Not Found: " + request.getCompanyId()));

        // Only company owner can create post
        if (!company.getOwnerId().equals(user.getUserId())) {

            throw new UnauthorizedException("You are not authorized to create post for this company");
        }

        // DTO -> Entity
        CompanyPost post = CompanyMapper.toEntity(request);

        // Image upload
        if (request.getImage() != null && !request.getImage().isEmpty()) {

            String imageUrl = cloudinaryService.uploadPostImage(request.getImage());

            post.setImageUrl(imageUrl);
        }

        // Save post
        CompanyPost savedPost = companyPostRepository.save(post);

        // Entity -> Response DTO
        return CompanyMapper.toResponseDto(savedPost);
    }

    @Override
    public CompanyPostResponseDto getByPostId(Long postId) {

        CompanyPost post = companyPostRepository.findById(postId)
                .orElseThrow(() -> new CompanyPostNotFoundException("Post Not Found: " + postId));

        return CompanyMapper.toResponseDto(post);
    }

    @Override
    public List<CompanyPostResponseDto> getAllPost() {

        return companyPostRepository.findAll()
                .stream()
                .map(CompanyMapper::toResponseDto)
                .toList();
    }
    @Override
    public CompanyPostResponseDto updatePost(
            CompanyUpdatePostRequestDto request,
            Long postId) {

        AuthenticatedUser user = getLoggedInUser();

        CompanyPost existingPost =
                companyPostRepository.findById(postId)
                        .orElseThrow(() -> new CompanyPostNotFoundException("Post Not Found: " + postId));

        // Find company using post's companyId
        Company company =
                companyRepository.findById(existingPost.getCompanyId())
                        .orElseThrow(() -> new CompanyNotFoundException("Company Not Found: " + existingPost.getCompanyId()));

        // Only company owner can update
        if (!company.getOwnerId().equals(user.getUserId())) {

            throw new UnauthorizedException("You are not authorized to update this post");
        }

        // Update text/details
        CompanyMapper.updatePostEntity(request, existingPost);

        // Update image if new image is provided
        if (request.getImage() != null && !request.getImage().isEmpty()) {

            String imageUrl =cloudinaryService.uploadPostImage(request.getImage());

            existingPost.setImageUrl(imageUrl);
        }

        CompanyPost updatedPost =
                companyPostRepository.save(existingPost);

        return CompanyMapper.toResponseDto(updatedPost);
    }

    @Override
    public void deletePost(Long postId) {

        AuthenticatedUser user = getLoggedInUser();

        CompanyPost post = companyPostRepository.findById(postId)
                .orElseThrow(() -> new CompanyPostNotFoundException("Post Not Found: " + postId));

        Company company = companyRepository.findById(post.getCompanyId())
                .orElseThrow(() -> new CompanyNotFoundException("Company Not Found: " + post.getCompanyId()));

        // Only company owner can delete
        if (!company.getOwnerId().equals(user.getUserId())) {

            throw new UnauthorizedException("You are not authorized to delete this post");
        }

        companyPostRepository.delete(post);
    }

    @Override
    public List<Long> getPostIdsByCompany(Long companyId) {

        return companyPostRepository.findByCompanyId(companyId)
                .stream()
                .map(CompanyPost::getPostId)
                .toList();
    }

    @Override
    public List<CompanyPostResponseDto> getPostsByCompany(Long companyId) {

        // Check company exists
        companyRepository.findById(companyId)
                .orElseThrow(() ->
                        new CompanyNotFoundException(
                                "Company Not Found: " + companyId
                        )
                );

        // Fetch only posts belonging to this company
        return companyPostRepository.findByCompanyId(companyId)
                .stream()
                .map(CompanyMapper::toResponseDto)
                .toList();
    }
}
