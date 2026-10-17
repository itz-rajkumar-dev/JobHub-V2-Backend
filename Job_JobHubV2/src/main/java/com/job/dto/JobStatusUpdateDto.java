package com.job.dto;
import com.job.entity.JobStatus;

import jakarta.validation.constraints.NotNull;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobStatusUpdateDto {

    @NotNull(message = "Status is required")
    private JobStatus status;
}