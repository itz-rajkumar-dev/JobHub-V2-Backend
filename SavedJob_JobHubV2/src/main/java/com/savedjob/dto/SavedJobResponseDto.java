package com.savedjob.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SavedJobResponseDto {

    private Long savedJobId;
    private Long userId;
    private Long jobId;
    private LocalDateTime savedAt;
}
