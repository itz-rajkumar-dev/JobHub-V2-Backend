package com.company.serviceImpl;

import com.company.client.ApplicationClient;
import com.company.client.EngagementClient;
import com.company.client.JobClient;
import com.company.client.SavedJobClient;
import com.company.cloudinary.CloudinaryService;
import com.company.dto.*;
import com.company.entity.Company;
import com.company.entity.CompanyPost;
import com.company.exception.CompanyAlreadyExistException;
import com.company.exception.CompanyLifeNotFoundException;
import com.company.exception.CompanyNotFoundException;
import com.company.exception.UnauthorizedException;
import com.company.mapper.CompanyMapper;
import com.company.repository.CompanyLifeRepository;
import com.company.repository.CompanyPostRepository;
import com.company.repository.CompanyRepository;
import com.company.security.AuthenticatedUser;
import com.company.service.CompanyService;
import com.company.storage.FileStorageService;
import jakarta.transaction.Transactional;
import org.apache.commons.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private JobClient jobClient;

    @Autowired
    private ApplicationClient applicationClient;

    @Autowired
    private EngagementClient engagementClient;

    @Autowired
    private SavedJobClient savedJobClient;

    @Autowired
    private CompanyPostRepository companyPostRepository;

    @Autowired
    private CompanyLifeRepository companyLifeRepository;

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
    public CompanyResponseDto createCompany(
            CompanyRequestDto companyRequestDto) {

        AuthenticatedUser user=getLoggedInUser();
        // Company name duplicate check
        if (companyRepository
                .findByCompanyName(companyRequestDto.getCompanyName())
                .isPresent()) {

            throw new CompanyAlreadyExistException(
                    "Company Already Exist: " + companyRequestDto.getCompanyName());
        }

        // Registration number duplicate check
        if (companyRequestDto.getRegistrationNumber() != null
                && companyRepository
                .findByRegistrationNumber(companyRequestDto.getRegistrationNumber())
                .isPresent()) {

            throw new CompanyAlreadyExistException(
                    "Registration Number Already Exist: " + companyRequestDto.getRegistrationNumber());
        }


        Company company = CompanyMapper.toEntity(companyRequestDto);

        // JWT se ownerId
        company.setOwnerId(user.getUserId());

        if (companyRequestDto.getLogo() != null &&
                !companyRequestDto.getLogo().isEmpty()) {

            String logoUrl = cloudinaryService.uploadCompanyLogo(companyRequestDto.getLogo());

            company.setLogoUrl(logoUrl);
        }

        if (companyRequestDto.getCover() != null &&
                !companyRequestDto.getCover().isEmpty()) {

            String coverUrl =
                    cloudinaryService.uploadCompanyCover(companyRequestDto.getCover());

            company.setCoverUrl(coverUrl);
        }
        Company savedCompany = companyRepository.save(company);

        return CompanyMapper.toResponseDto(savedCompany);
    }

    @Override
    public List<CompanyResponseDto> getAllCompany() {

        return companyRepository.findAll()
                .stream()
                .map(CompanyMapper::toResponseDto)
                .toList();
    }
    @Override
    public List<CompanyResponseDto> getCompaniesByOwnerId(Long ownerId) {

        return companyRepository.findByOwnerId(ownerId)
                .stream()
                .map(CompanyMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<CompanyResponseDto> getMyCompany() {
        AuthenticatedUser user = getLoggedInUser();

        return companyRepository.findByOwnerId(user.getUserId())
                .stream()
                .map(CompanyMapper::toResponseDto)
                .toList();
    }

    @Override
    public CompanyResponseDto getByCompanyId(Long companyId) {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException("Company Not Found: " + companyId));

        return CompanyMapper.toResponseDto(company);
    }

    @Override
    public CompanyResponseDto updateCompany(
            CompanyUpdateRequestDto dto,
            Long companyId) {

        AuthenticatedUser user = getLoggedInUser();


        Company existingCompany = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException("Company Not Found: " + companyId));



        // Only owner can update
        if (!existingCompany.getOwnerId().equals(user.getUserId())) {

            throw new UnauthorizedException("You are not authorized to update this company");
        }

       CompanyMapper.updateEntity(dto,existingCompany);

        if (dto.getLogo() != null && !dto.getLogo().isEmpty()) {

            String logoUrl = cloudinaryService.uploadCompanyLogo(dto.getLogo());
            existingCompany.setLogoUrl(logoUrl);
        }

        if (dto.getCover() != null &&
                !dto.getCover().isEmpty()) {

            String coverUrl = cloudinaryService.uploadCompanyCover(dto.getCover());

            existingCompany.setCoverUrl(coverUrl);
        }

        Company updatedCompany = companyRepository.save(existingCompany);

        return CompanyMapper.toResponseDto(updatedCompany);
    }

    @Override
    public void deleteCompany(Long companyId) {

        AuthenticatedUser user = getLoggedInUser();

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException("Company Not Found: " + companyId));



        // Only owner can delete
        if (!company.getOwnerId().equals(user.getUserId())) {

            throw new UnauthorizedException("You are not authorized to delete this company");
        }

        companyRepository.delete(company);
    }


    @Override
    public CompanyDeleteCheckResponseDto checkCompanyDelete(Long companyId) {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() ->
                        new CompanyNotFoundException("Company not found: " + companyId)
                );

        // Ownership check
        AuthenticatedUser user = getLoggedInUser();

        if (!company.getOwnerId().equals(user.getUserId())) {
            throw new UnauthorizedException(
                    "You are not authorized to check this company"
            );
        }

        // JobService
        long jobs = jobClient.countJobsByCompany(companyId);

        // ApplicationService
        long applications =
                applicationClient.countApplicationsByCompany(companyId);

        // SavedJobService
        long savedJobs =
                savedJobClient.countSavedJobsByCompany(companyId);

        // CompanyService DB
        long posts =
                companyPostRepository.countByCompanyId(companyId);

        long companyLifePosts =
                companyLifeRepository.countByCompanyId(companyId);

        // EngagementService
        long followers =
                engagementClient.countCompanyFollowers(companyId);

        long likes =
                engagementClient.countCompanyLikes(companyId);

        long comments =
                engagementClient.countCompanyComments(companyId);

        long postLikes =
                engagementClient.countCompanyPostLikes(companyId);

        long postComments =
                engagementClient.countCompanyPostComments(companyId);

        boolean canDelete =
                jobs == 0 &&
                        applications == 0 &&
                        savedJobs == 0 &&
                        posts == 0 &&
                        companyLifePosts == 0 &&
                        followers == 0 &&
                        likes == 0 &&
                        comments == 0 &&
                        postLikes == 0 &&
                        postComments == 0;

        return CompanyDeleteCheckResponseDto.builder()
                .companyId(company.getCompanyId())
                .companyName(company.getCompanyName())
                .canDelete(canDelete)
                .jobs(jobs)
                .applications(applications)
                .savedJobs(savedJobs)
                .posts(posts)
                .companyLifePosts(companyLifePosts)
                .followers(followers)
                .likes(likes)
                .comments(comments)
                .postLikes(postLikes)
                .postComments(postComments)
                .build();
    }


    @Override
    public long countCompaniesByOwner(Long ownerId) {
        return companyRepository.countByOwnerId(ownerId);
    }

    @Override
    @Transactional
    public void incrementProfileViews(Long companyId) {

        if (!companyRepository.existsById(companyId)) {
            throw new CompanyNotFoundException(
                    "Company Not Found: " + companyId
            );
        }
        companyRepository.incrementProfileViews(companyId);
    }

    @Override
    public long getProfileViews(Long companyId) {

        AuthenticatedUser user = getLoggedInUser();

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException("Company Not Found: " + companyId));


        if (!user.getUserId().equals(company.getOwnerId())) {
            throw new UnauthorizedException("You are not authorized to view this company's profile views");
        }

        return company.getProfileViews();
    }
    @Override
    public long countProfileViewsByOwner(Long ownerId) {
        return companyRepository.countProfileViewsByOwnerId(ownerId);
    }
}