CREATE TABLE IF NOT EXISTS hotels (
    hotel_id SERIAL PRIMARY KEY,
    hotel_name VARCHAR(100),
    city VARCHAR(50),
    rating DECIMAL(2,1),
    price_per_night DECIMAL(10,2)
);

CREATE TABLE IF NOT EXISTS rooms (
    room_id SERIAL PRIMARY KEY,
    hotel_id INT REFERENCES hotels(hotel_id),
    room_type VARCHAR(50),
    available_rooms INT
);

CREATE TABLE IF NOT EXISTS hotel_amenities (
    amenity_id SERIAL PRIMARY KEY,
    hotel_id INT REFERENCES hotels(hotel_id),
    amenity_name VARCHAR(50)
);
