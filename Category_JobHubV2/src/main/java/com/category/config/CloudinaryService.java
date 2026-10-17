package com.category.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadIcon(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Category icon is empty");
        }

        if (file.getSize() > 2 * 1024 * 1024) {
            throw new IllegalArgumentException(
                    "Category icon must not exceed 2 MB");
        }

        String contentType = file.getContentType();

        if (contentType == null ||
                !(contentType.equals("image/png")
                        || contentType.equals("image/jpeg")
                        || contentType.equals("image/webp"))) {
            throw new IllegalArgumentException(
                    "Only PNG, JPEG and WEBP images are allowed");
        }

        try {
            Map<?, ?> result = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "jobhub/category",
                            "resource_type", "image"
                    )
            );

            return result.get("secure_url").toString();

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to upload category icon to Cloudinary", e);
        }
    }
}