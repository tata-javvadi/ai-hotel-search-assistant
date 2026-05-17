package com.tatajavvadi.ai_hotel_search.model;

import jakarta.validation.constraints.NotBlank;

public record HotelSearchRequest(@NotBlank(message = "Query must not be blank") String query) {
}
