-- =========================================
-- HOTELS
-- =========================================

INSERT INTO hotels (hotel_id, hotel_name, city, rating, price_per_night)
VALUES
(1, 'Sea View Resort', 'Goa', 4.5, 4500),

(2, 'Royal Stay', 'Hyderabad', 4.2, 3200),

(3, 'Ocean Breeze', 'Goa', 4.8, 6000),

(4, 'City Comfort Inn', 'Bangalore', 4.1, 2800),

(5, 'Mountain Escape', 'Manali', 4.7, 5200),

(6, 'Luxury Palace', 'Jaipur', 4.9, 8500),

(7, 'Beach Paradise', 'Goa', 4.3, 3900),

(8, 'Business Elite', 'Mumbai', 4.4, 7100)
ON CONFLICT (hotel_id) DO NOTHING;



-- =========================================
-- ROOMS
-- =========================================

INSERT INTO rooms (room_id, hotel_id, room_type, available_rooms)
VALUES
(1, 1, 'Deluxe', 5),

(2, 1, 'Suite', 2),

(3, 2, 'Standard', 8),

(4, 3, 'Deluxe', 3),

(5, 4, 'Standard', 10),

(6, 5, 'Suite', 1),

(7, 6, 'Presidential', 1),

(8, 7, 'Deluxe', 4),

(9, 8, 'Business', 6)
ON CONFLICT (room_id) DO NOTHING;



-- =========================================
-- HOTEL AMENITIES
-- =========================================

INSERT INTO hotel_amenities (amenity_id, hotel_id, amenity_name)
VALUES
(1, 1, 'Wifi'),

(2, 1, 'Swimming Pool'),

(3, 1, 'Parking'),

(4, 2, 'Wifi'),

(5, 2, 'Parking'),

(6, 3, 'Wifi'),

(7, 3, 'Swimming Pool'),

(8, 3, 'Gym'),

(9, 4, 'Wifi'),

(10, 5, 'Mountain View'),

(11, 5, 'Bonfire'),

(12, 6, 'Spa'),

(13, 6, 'Wifi'),

(14, 6, 'Swimming Pool'),

(15, 7, 'Beach Access'),

(16, 7, 'Wifi'),

(17, 8, 'Conference Hall'),

(18, 8, 'Wifi')
ON CONFLICT (amenity_id) DO NOTHING;

SELECT setval('hotels_hotel_id_seq', COALESCE((SELECT MAX(hotel_id) FROM hotels), 1), true);
SELECT setval('rooms_room_id_seq', COALESCE((SELECT MAX(room_id) FROM rooms), 1), true);
SELECT setval('hotel_amenities_amenity_id_seq', COALESCE((SELECT MAX(amenity_id) FROM hotel_amenities), 1), true);
