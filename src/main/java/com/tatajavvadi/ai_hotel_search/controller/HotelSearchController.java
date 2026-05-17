package com.tatajavvadi.ai_hotel_search.controller;

import com.tatajavvadi.ai_hotel_search.model.HotelSearchRequest;
import com.tatajavvadi.ai_hotel_search.model.HotelSearchResponse;
import com.tatajavvadi.ai_hotel_search.service.HotelSearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
public class HotelSearchController {

    private final HotelSearchService hotelSearchService;

    @PostMapping("/search")
    public ResponseEntity<HotelSearchResponse> searchHotels(@Valid @RequestBody HotelSearchRequest request) {
        log.info("Received hotel search request: {}", request.query());
        return ResponseEntity.ok(hotelSearchService.search(request.query()));
    }
}
