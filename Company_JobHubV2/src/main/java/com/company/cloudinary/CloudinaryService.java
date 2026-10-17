package com.company.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadPostImage(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Post image is empty");
        }

        try {

            Map<?, ?> result = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "jobhub/company-posts",
                            "resource_type", "image"
                    )
            );

            return result.get("secure_url").toString();

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to upload image to Cloudinary", e
            );
        }
    }

    public String uploadCompanyLogo(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Company logo is empty");
        }

        try {

            Map<?, ?> result = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "jobhub/company-logos",
                            "resource_type", "image"
                    )
            );

            return result.get("secure_url").toString();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to upload company logo to Cloudinary",
                    e
            );
        }
    }

    public String uploadCompanyCover(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Company cover is empty");
        }

        try {

            Map<?, ?> result = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "jobhub/company-covers",
                            "resource_type", "image"
                    )
            );

            return result.get("secure_url").toString();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to upload company cover to Cloudinary",
                    e
            );
        }
    }

    public String uploadCompanyLifeImage(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Company Life Image Empty");
        }

        try {

            Map<?, ?> result = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "jobhub/company-life",
                            "resource_type", "image"
                    )
            );

            return result.get("secure_url").toString();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to upload company life image to Cloudinary",
                    e
            );
        }
    }
}