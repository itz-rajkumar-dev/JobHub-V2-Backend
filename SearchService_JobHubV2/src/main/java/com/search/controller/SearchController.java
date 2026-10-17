package com.search.controller;

import com.search.dto.JobSearchResponseDto;
import com.search.dto.PageResponseDto;
import com.search.dto.SearchRequestDto;
import com.search.service.SearchService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public PageResponseDto searchJobs(@Valid @ModelAttribute SearchRequestDto requestDto) {

        return searchService.searchJobs(requestDto);
    }

    @GetMapping("/jobs/{jobId}")
    public JobSearchResponseDto getJobById(@PathVariable Long jobId) {

        return searchService.getJobById(jobId);
    }
}