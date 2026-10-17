package com.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobResponseDto {

    private Long jobId;
    private String title;
    private Long companyId;
    private String status;
}
