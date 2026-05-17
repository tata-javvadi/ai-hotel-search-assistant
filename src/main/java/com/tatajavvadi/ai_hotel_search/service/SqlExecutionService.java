package com.tatajavvadi.ai_hotel_search.service;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SqlExecutionService {

    private final JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> executeSelect(String sql) {
        log.debug("Executing validated SQL: {}", sql);
        return jdbcTemplate.query(sql, new ColumnMapRowMapper());
    }
}
