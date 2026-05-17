package com.tatajavvadi.ai_hotel_search.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SqlGenerationService {

    private static final String SYSTEM_PROMPT = """
            You translate hotel search questions into PostgreSQL SELECT queries.

            Rules:
            - Return only raw SQL. Do not wrap it in markdown.
            - Only generate one SELECT statement.
            - Never generate INSERT, UPDATE, DELETE, DROP, ALTER, CREATE, TRUNCATE, or comments.
            - Only use these tables and columns:
              hotels(hotel_id, hotel_name, city, rating, price_per_night)
              rooms(room_id, hotel_id, room_type, available_rooms)
              hotel_amenities(amenity_id, hotel_id, amenity_name)
            - Use case-insensitive filtering when matching city, room type, or amenity names.
            - Use joins only when needed.
            - For amenity filters requiring multiple amenities, ensure the hotel satisfies all requested amenities.
            - For top-rated requests, order by rating DESC.
            - For available rooms, enforce available_rooms > 0.
            - Prefer readable aliases.
            - Include a LIMIT 20 unless the question asks for a different limit.

            Example patterns:
            - Hotels in Goa under 5000
            - Hotels with Wifi and Swimming Pool
            - Top rated hotels in Hyderabad
            - Hotels with available Deluxe rooms
            """;

    private final ChatClient chatClient;

    public SqlGenerationService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String generateSql(String naturalLanguageQuery) {
        log.debug("Generating SQL for natural language query: {}", naturalLanguageQuery);
        String response = chatClient.prompt()
                .system(SYSTEM_PROMPT)
                .user("Convert this hotel search request into SQL: " + naturalLanguageQuery)
                .call()
                .content();

        if (response == null || response.isBlank()) {
            log.warn("The model returned an empty response for query: {}", naturalLanguageQuery);
            throw new SqlGenerationException("The model did not return a SQL query.");
        }

        String sanitizedSql = sanitize(response);
        log.debug("Sanitized SQL response: {}", sanitizedSql);
        return sanitizedSql;
    }

    private String sanitize(String response) {
        String sql = response.trim();
        if (sql.startsWith("```")) {
            sql = sql.replaceFirst("^```sql\\s*", "")
                    .replaceFirst("^```\\s*", "")
                    .replaceFirst("\\s*```$", "")
                    .trim();
        }
        return sql;
    }
}
