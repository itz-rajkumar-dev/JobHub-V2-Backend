package com.company.serviceImpl;


import com.company.cloudinary.CloudinaryService;
import com.company.entity.Company;
import com.company.entity.CompanyLife;
import com.company.dto.CompanyLifeRequestDto;
import com.company.dto.CompanyLifeResponseDto;
import com.company.exception.CompanyLifeNotFoundException;
import com.company.exception.CompanyNotFoundException;
import com.company.exception.UnauthorizedException;
import com.company.mapper.CompanyLifeMapper;
import com.company.repository.CompanyLifeRepository;
import com.company.repository.CompanyRepository;
import com.company.security.AuthenticatedUser;
import com.company.service.CompanyLifeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyLifeServiceImpl implements CompanyLifeService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyLifeRepository companyLifeRepository;

    @Autowired
    private CloudinaryService cloudinaryService;


    // Logged-in user
    private AuthenticatedUser getLoggedUser() {

        return (AuthenticatedUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }


    // Check company ownership
    private Company getOwnedCompany(Long companyId) {

        AuthenticatedUser user = getLoggedUser();

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() ->
                        new CompanyNotFoundException(
                                "Company Not Found: " + companyId
                        )
                );

        if (!company.getOwnerId().equals(user.getUserId())) {

            throw new UnauthorizedException(
                    "You are not authorized to manage this company"
            );
        }

        return company;
    }


    // =========================
    // CREATE
    // =========================

    @Override
    public CompanyLifeResponseDto createCompanyLife(
            Long companyId,
            CompanyLifeRequestDto requestDto) {

        // Ownership check
        getOwnedCompany(companyId);

        if (requestDto.getImage() == null ||
                requestDto.getImage().isEmpty()) {

            throw new IllegalArgumentException(
                    "Company life image is required"
            );
        }

        // Upload image to Cloudinary
        String imageUrl =
                cloudinaryService.uploadCompanyLifeImage(
                        requestDto.getImage()
                );

        CompanyLife life = CompanyLifeMapper.toEntity(requestDto, companyId, imageUrl);
        CompanyLife savedLife = companyLifeRepository.save(life);

        return CompanyLifeMapper.toResponseDto(savedLife);
    }


    // =========================
    // UPDATE
    // =========================

    @Override
    public CompanyLifeResponseDto updateCompanyLife(
            Long companyId,
            Long lifeId,
            CompanyLifeRequestDto requestDto) {

        getOwnedCompany(companyId);

        CompanyLife life =
                companyLifeRepository.findById(lifeId)
                        .orElseThrow(() -> new CompanyLifeNotFoundException("Company Life Not Found: " + lifeId));

        if (!life.getCompanyId().equals(companyId)) {
            throw new UnauthorizedException(
                    "This content does not belong to this company"
            );
        }

        String imageUrl = null;

        if (requestDto.getImage() != null && !requestDto.getImage().isEmpty()) {

            imageUrl = cloudinaryService.uploadCompanyLifeImage(requestDto.getImage());
        }

        CompanyLifeMapper.updateEntity(requestDto, life, imageUrl);

        CompanyLife updatedLife = companyLifeRepository.save(life);

        return CompanyLifeMapper.toResponseDto(updatedLife);
    }

    // =========================
    // GET
    // =========================

    @Override
    public List<CompanyLifeResponseDto> getCompanyLife(
            Long companyId) {

        // Public endpoint
        companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException("Company Not Found: " + companyId));

        return companyLifeRepository
                .findByCompanyIdOrderByDisplayOrderAsc(companyId)
                .stream()
                .map(CompanyLifeMapper::toResponseDto)
                .toList();
    }


    // =========================
    // DELETE
    // =========================

    @Override
    public void deleteCompanyLife(
            Long companyId,
            Long lifeId) {

        // Ownership check
        getOwnedCompany(companyId);

        CompanyLife life = companyLifeRepository.findById(lifeId)
                        .orElseThrow(() -> new RuntimeException("Company Life Not Found: " + lifeId));

        if (!life.getCompanyId().equals(companyId)) {

            throw new UnauthorizedException("This content does not belong to this company");
        }

        companyLifeRepository.delete(life);
    }



}