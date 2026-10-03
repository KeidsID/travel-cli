package models.stays;

import java.time.ZonedDateTime;

public record StayGuest(String bookingId, int roomCount, ZonedDateTime checkIn, ZonedDateTime checkOut) {
    public StayGuest {
        if (bookingId.isBlank()) {
            throw new IllegalArgumentException("StayGuest.bookingId cannot be blank");
        }
        if (roomCount <= 0) {
            throw new IllegalArgumentException("StayGuest.roomCount cannot be negative");
        }
        if (checkIn.isAfter(checkOut)) {
            throw new IllegalArgumentException("StayGuest.checkOut must be after StayGuest.checkIn");
        }
    }
}
