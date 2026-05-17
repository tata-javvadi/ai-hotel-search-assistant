package com.tatajavvadi.ai_hotel_search.service;

import java.util.List;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SqlValidationService {

    private static final List<String> ALLOWED_TABLES = List.of("hotels", "rooms", "hotel_amenities");
    private static final List<String> BLOCKED_KEYWORDS = List.of(
            "insert", "update", "delete", "drop", "alter", "truncate", "create",
            "grant", "revoke", "merge", "call", "execute", "comment"
    );

    public void validate(String sql) {
        String normalized = sql == null ? "" : sql.trim();
        String lowerCaseSql = normalized.toLowerCase(Locale.ROOT);
        log.debug("Validating SQL: {}", normalized);

        if (normalized.isBlank()) {
            log.warn("Generated SQL validation failed because the SQL was blank.");
            throw new IllegalArgumentException("Generated SQL is blank.");
        }

        if (!lowerCaseSql.startsWith("select")) {
            log.warn("Generated SQL validation failed because the statement is not a SELECT query.");
            throw new IllegalArgumentException("Only SELECT queries are allowed.");
        }

        if (lowerCaseSql.contains(";")) {
            log.warn("Generated SQL validation failed because multiple statements were detected.");
            throw new IllegalArgumentException("Multiple statements are not allowed.");
        }

        for (String keyword : BLOCKED_KEYWORDS) {
            if (containsWord(lowerCaseSql, keyword)) {
                log.warn("Generated SQL validation failed because blocked keyword [{}] was detected.", keyword);
                throw new IllegalArgumentException("Blocked SQL keyword detected: " + keyword);
            }
        }

        if (containsWord(lowerCaseSql, "from") && ALLOWED_TABLES.stream().noneMatch(lowerCaseSql::contains)) {
            log.warn("Generated SQL validation failed because unsupported tables were referenced.");
            throw new IllegalArgumentException("Query references tables outside the supported hotel schema.");
        }

        log.debug("SQL validation completed successfully.");
    }

    private boolean containsWord(String text, String word) {
        return text.matches("(?s).*\\b" + word + "\\b.*");
    }
}
