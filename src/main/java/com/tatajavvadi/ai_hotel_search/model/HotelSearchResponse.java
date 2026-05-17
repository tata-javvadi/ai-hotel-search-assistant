package com.tatajavvadi.ai_hotel_search.model;

import java.util.List;
import java.util.Map;

public record HotelSearchResponse(
        String naturalLanguageQuery,
        String generatedSql,
        boolean valid,
        int rowCount,
        List<Map<String, Object>> results
) {
}
