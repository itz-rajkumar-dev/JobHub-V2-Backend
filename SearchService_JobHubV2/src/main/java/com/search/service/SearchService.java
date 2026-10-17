package com.search.service;

import com.search.dto.JobSearchResponseDto;
import com.search.dto.PageResponseDto;
import com.search.dto.SearchRequestDto;


public interface SearchService {


   PageResponseDto searchJobs(SearchRequestDto requestDto);

   JobSearchResponseDto getJobById(Long jobId);

}
