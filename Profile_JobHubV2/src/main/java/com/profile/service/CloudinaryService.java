package com.profile.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadCoverImage(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Cover image is empty");
        }

        try {

            Map<?, ?> result = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "jobhub/profile-covers",
                            "resource_type", "image"
                    )
            );

            return result.get("secure_url").toString();

        } catch (IOException e) {

            throw new RuntimeException(
                    "Could not upload cover image to Cloudinary",
                    e
            );
        }
    }

    public String uploadEmployerProfileImage(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Employer profile image is empty");
        }

        try {

            Map<?, ?> result = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "jobhub/employer-profile",
                            "resource_type", "image"
                    )
            );

            return result.get("secure_url").toString();

        } catch (IOException e) {

            throw new RuntimeException(
                    "Could not upload employer profile image to Cloudinary",
                    e
            );
        }
    }

    public String uploadEmployerCoverImage(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Employer cover image is empty");
        }

        try {

            Map<?, ?> result = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "jobhub/employer-covers",
                            "resource_type", "image"
                    )
            );

            return result.get("secure_url").toString();

        } catch (IOException e) {

            throw new RuntimeException(
                    "Could not upload employer cover image to Cloudinary",
                    e
            );
        }
    }
}