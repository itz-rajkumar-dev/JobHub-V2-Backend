package com.application.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponseDto<T> {

    private List<T> content;

    private int page;

    private int size;

    private long totalElements;

    private int totalPages;

    private boolean hasNext;

    private boolean hasPrevious;

    private long appliedCount;
    private long underReviewCount;
    private long shortlistedCount;
    private long interviewScheduledCount;
    private long interviewCompletedCount;
    private long offeredCount;
    private long hiredCount;
    private long rejectedCount;
    private long withdrawCount;

}
