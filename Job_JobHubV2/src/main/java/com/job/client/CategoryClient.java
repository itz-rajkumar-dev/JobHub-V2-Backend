package com.job.client;

import com.job.dto.CategoryResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="CategoryService")
public interface CategoryClient {

    @GetMapping("/category/{categoryId}")
    public CategoryResponseDto getByCategoryId(@PathVariable("categoryId") Long categoryId);



}
