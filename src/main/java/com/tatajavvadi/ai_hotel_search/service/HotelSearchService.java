package com.tatajavvadi.ai_hotel_search.service;

import com.tatajavvadi.ai_hotel_search.model.HotelSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelSearchService {

    private final SqlGenerationService sqlGenerationService;
    private final SqlValidationService sqlValidationService;
    private final SqlExecutionService sqlExecutionService;

    public HotelSearchResponse search(String naturalLanguageQuery) {
        log.info("Starting hotel search workflow for query: {}", naturalLanguageQuery);
        String generatedSql = sqlGenerationService.generateSql(naturalLanguageQuery);
        log.info("Generated SQL: {}", generatedSql);
        sqlValidationService.validate(generatedSql);
        log.debug("SQL validation passed for query: {}", naturalLanguageQuery);

        var results = sqlExecutionService.executeSelect(generatedSql);
        log.info("SQL execution completed with {} result rows.", results.size());
        return new HotelSearchResponse(
                naturalLanguageQuery,
                generatedSql,
                true,
                results.size(),
                results
        );
    }
}
