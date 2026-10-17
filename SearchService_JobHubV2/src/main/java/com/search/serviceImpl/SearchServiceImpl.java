package com.search.serviceImpl;

import com.search.dto.JobSearchResponseDto;
import com.search.dto.PageResponseDto;
import com.search.dto.SearchRequestDto;
import com.search.service.SearchService;
import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.FieldValue;
import org.opensearch.client.opensearch._types.SortOrder;
import org.opensearch.client.opensearch._types.query_dsl.Query;
import org.opensearch.client.opensearch._types.query_dsl.TextQueryType;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private final OpenSearchClient openSearchClient;

    public SearchServiceImpl(OpenSearchClient openSearchClient) {
        this.openSearchClient = openSearchClient;
    }

    @Override
    public PageResponseDto searchJobs(SearchRequestDto requestDto) {

        List<Query> filters = new ArrayList<>();

        // =====================================================
        // 1. ONLY OPEN JOBS
        // =====================================================

        filters.add(
                Query.of(q -> q.term(
                        t -> t
                                .field("status")
                                .value(v -> v.stringValue("OPEN"))
                ))
        );


        // =====================================================
        // 2. KEYWORD
        // =====================================================


        if (requestDto.getKeyword() != null &&
                !requestDto.getKeyword().isBlank()) {

            String keyword=requestDto.getKeyword().trim().toUpperCase();
            filters.add(
                    Query.of(q -> q.multiMatch(m -> m
                            .query(keyword)
                            .fields(
                                    "title",
                                    "companyName",
                                    "skills"
                            )
                            .type(TextQueryType.BoolPrefix)
                    ))
            );
        }

        // =====================================================
// 3. LOCATION
// =====================================================

        if (requestDto.getLocation() != null &&
                !requestDto.getLocation().isBlank()) {

            String location = requestDto.getLocation().trim();

            filters.add(
                    Query.of(q -> q.match(m -> m
                            .field("location")
                            .query(FieldValue.of(location))
                            .fuzziness("AUTO")
                    ))
            );
        }


        // =====================================================
        // 4. JOB TYPE
        // =====================================================

        if (requestDto.getJobType() != null &&
                !requestDto.getJobType().isBlank()) {

            String jobType = requestDto.getJobType()
                    .trim()
                    .toUpperCase();

            filters.add(
                    Query.of(q -> q.term(
                            t -> t
                                    .field("jobType")
                                    .value(v -> v.stringValue(jobType))
                    ))
            );
        }


        // =====================================================
        // 5. WORK MODE
        // =====================================================

        if (requestDto.getWorkMode() != null &&
                !requestDto.getWorkMode().isBlank()) {

            String workMode = requestDto.getWorkMode()
                    .trim()
                    .toUpperCase();

            filters.add(
                    Query.of(q -> q.term(
                            t -> t
                                    .field("workMode")
                                    .value(v -> v.stringValue(workMode))
                    ))
            );
        }


        // =====================================================
        // 6. CATEGORY
        // =====================================================

        if (requestDto.getCategoryId() != null) {

            filters.add(
                    Query.of(q -> q.term(
                            t -> t
                                    .field("categoryId")
                                    .value(v -> v.longValue(
                                            requestDto.getCategoryId()
                                    ))
                    ))
            );
        }


        // =====================================================
        // 7. COMPANY
        // =====================================================

        if (requestDto.getCompanyId() != null) {

            filters.add(
                    Query.of(q -> q.term(
                            t -> t
                                    .field("companyId")
                                    .value(v -> v.longValue(
                                            requestDto.getCompanyId()
                                    ))
                    ))
            );
        }


        // =====================================================
        // 8. MINIMUM SALARY
        // salaryMax >= minSalary
        // =====================================================

        if (requestDto.getMinSalary() != null) {

            filters.add(
                    Query.of(q -> q.range(
                            r -> r
                                    .field("salaryMax")
                                    .gte(JsonData.of(
                                            requestDto.getMinSalary()
                                    ))
                    ))
            );
        }


        // =====================================================
        // 9. MAXIMUM SALARY
        // salaryMin <= maxSalary
        // =====================================================

        if (requestDto.getMaxSalary() != null) {

            filters.add(
                    Query.of(q -> q.range(
                            r -> r
                                    .field("salaryMin")
                                    .lte(JsonData.of(
                                            requestDto.getMaxSalary()
                                    ))
                    ))
            );
        }


        // =====================================================
        // 10. MINIMUM EXPERIENCE
        // experienceMax >= minExperience
        // =====================================================

        if (requestDto.getMinExperience() != null) {

            filters.add(
                    Query.of(q -> q.range(
                            r -> r
                                    .field("experienceMax")
                                    .gte(JsonData.of(
                                            requestDto.getMinExperience()
                                    ))
                    ))
            );
        }


        // =====================================================
        // 11. MAXIMUM EXPERIENCE
        // experienceMin <= maxExperience
        // =====================================================

        if (requestDto.getMaxExperience() != null) {

            filters.add(
                    Query.of(q -> q.range(
                            r -> r
                                    .field("experienceMin")
                                    .lte(JsonData.of(
                                            requestDto.getMaxExperience()
                                    ))
                    ))
            );
        }


        // =====================================================
        // 12. SKILL
        // =====================================================

        if (requestDto.getSkill() != null &&
                !requestDto.getSkill().isBlank()) {

            String skill = requestDto.getSkill().trim();

            filters.add(
                    Query.of(q -> q.term(
                            t -> t
                                    .field("skills")
                                    .value(v -> v.stringValue(skill))
                    ))
            );
        }


        // =====================================================
        // 13. FINAL BOOL QUERY
        // =====================================================

        Query finalQuery = Query.of(q -> q.bool(
                b -> b.filter(filters)
        ));


        // =====================================================
        // 14. SORTING
        // =====================================================

        String requestedSortBy = requestDto.getSortBy();

        final String sortBy =
                (requestedSortBy == null || requestedSortBy.isBlank())
                        ? "createdAt"
                        : requestedSortBy;

        String requestedSortDirection = requestDto.getSortDirection();

        final SortOrder sortOrder =
                "asc".equalsIgnoreCase(requestedSortDirection)
                        ? SortOrder.Asc
                        : SortOrder.Desc;

        // =====================================================
        // 15. PAGINATION
        // =====================================================

        int page = requestDto.getPage();
        int size = requestDto.getSize();

        int from = page * size;


        // =====================================================
        // 16. SEARCH OPENSEARCH
        // =====================================================

        try {

            SearchResponse<JobSearchResponseDto> response =
                    openSearchClient.search(
                            s -> s
                                    .index("jobs")
                                    .query(finalQuery)
                                    .from(from)
                                    .size(size)
                                    .sort(
                                            so -> so.field(
                                                    f -> f
                                                            .field(sortBy)
                                                            .order(sortOrder)
                                            )
                                    ),
                            JobSearchResponseDto.class
                    );


            // =================================================
            // 17. CONVERT HITS → DTO
            // =================================================

            List<JobSearchResponseDto> content =
                    response.hits()
                            .hits()
                            .stream()
                            .map(hit -> hit.source())
                            .filter(source -> source != null)
                            .toList();


            // =================================================
            // 18. TOTAL ELEMENTS
            // =================================================

            long totalElements;

            if (response.hits().total() != null) {

                totalElements =
                        response.hits()
                                .total()
                                .value();

            } else {

                totalElements = content.size();
            }


            // =================================================
            // 19. TOTAL PAGES
            // =================================================

            int totalPages;

            if (size == 0) {

                totalPages = 0;

            } else {

                totalPages =
                        (int) Math.ceil(
                                (double) totalElements / size
                        );
            }


            // =================================================
            // 20. RETURN RESPONSE
            // =================================================

            return PageResponseDto.builder()
                    .content(content)
                    .page(page)
                    .size(size)
                    .totalElements(totalElements)
                    .totalPages(totalPages)
                    .hasNext(page < totalPages - 1)
                    .hasPrevious(page > 0)
                    .build();


        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to search jobs in OpenSearch",
                    e
            );
        }
    }

    @Override
    public JobSearchResponseDto getJobById(Long jobId) {

        try {

            return openSearchClient.get(
                    g -> g
                            .index("jobs")
                            .id(String.valueOf(jobId)),
                    JobSearchResponseDto.class
            ).source();

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to get job from OpenSearch",
                    e
            );
        }
    }
}